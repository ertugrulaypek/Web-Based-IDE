package edu.tum.ase.darkmode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DarkmodeController {

    private boolean darkModeStatus = false;

    @GetMapping("/dark-mode/toggle")
    public void toggleDarkMode() {
        darkModeStatus = !darkModeStatus;
    }

    @GetMapping("/dark-mode")
    public boolean getDarkMode() {
        return darkModeStatus;
    }
}
