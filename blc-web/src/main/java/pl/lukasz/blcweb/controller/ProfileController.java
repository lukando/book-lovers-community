package pl.lukasz.blcweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcservices.service.BackupService;
import pl.lukasz.blcservices.service.UserService;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final BackupService backupService;
    private final UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        AppUser user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String bio,
                                @RequestParam String avatarUrl,
                                Principal principal) {
        userService.updateProfile(principal.getName(), bio, avatarUrl);
        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Principal principal, HttpServletRequest request) {
        userService.deleteAccount(principal.getName());
        try {
            request.logout();
        } catch (Exception e) {
        }
        return "redirect:/register?deleted";
    }

    @GetMapping("/profile/backup")
    public ResponseEntity<ByteArrayResource> downloadBackup(Principal principal) {
        byte[] data = backupService.generateJsonBackup(principal.getName());
        ByteArrayResource resource = new ByteArrayResource(data);
        String filename = "backup_" + principal.getName() + "_" + LocalDate.now() + ".json";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(data.length)
                .body(resource);
    }
}