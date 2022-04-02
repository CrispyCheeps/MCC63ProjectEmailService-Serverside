//package co.id.emailservice.serverside.service;
//
//import co.id.emailservice.serverside.model.AppUserDetail;
//import co.id.emailservice.serverside.model.dto.RegisData;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class RegistrationService {
//
//    private final AppUserDetailService appUserDetailService;
//    private final EmailValidatorService emailValidatorService;
//
//    @SneakyThrows
//    public String register(RegisData regisData) {
//        boolean isValidEmail = emailValidatorService.
//                test(regisData.getEmail());
//        if (!isValidEmail) {
//            throw new IllegalAccessException("email not valid");
//        }
//        return appUserDetailService.signUpUser(
//               new AppUserDetail(
//                       regisData.getName(),
//                       regisData.getEmail(),
//                       regisData.getPassword()
//                       regisData.get
//
//               )
//        );
//    }
//}
