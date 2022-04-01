/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.emailservice.serverside.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Deanchristt
 */
@Data
public class UserData {
    
    private Long id;

    private String name;

    private String email;

    private String password;
    
    private Long roleId;
}
