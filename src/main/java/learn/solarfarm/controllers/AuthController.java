//package learn.solarfarm.controllers;
//
//import learn.solarfarm.domain.Result;
//import learn.solarfarm.models.AppUser;
//import learn.solarfarm.security.AppUserService;
//import learn.solarfarm.security.JwtConverter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtConverter converter;
//    private final AppUserService appUserService;
//
//    public AuthController(AuthenticationManager authenticationManager,
//                          JwtConverter converter, AppUserService appUserService) {
//        this.authenticationManager = authenticationManager;
//        this.converter = converter;
//        this.appUserService = appUserService;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> credentials) {
//
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(authToken);
//
//            if (authentication.isAuthenticated()) {
//                String jwtToken = converter.getTokenFromUser((UserDetails) authentication.getPrincipal());
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("jwt_token", jwtToken);
//
//                return new ResponseEntity<>(map, HttpStatus.OK);
//            }
//        } catch (AuthenticationException ex) {
//            System.out.println(ex);
//        }
//
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }
//
//    @PostMapping("/create_account")
//    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {
//
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//
//        Result<AppUser> result = appUserService.create(username, password);
//
//        if (!result.isSuccess()) {
//            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
//        }
//
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("appUserId", result.getPayload().getAppUserId());
//
//        return new ResponseEntity<>(map, HttpStatus.CREATED);
//
//    }
//}
