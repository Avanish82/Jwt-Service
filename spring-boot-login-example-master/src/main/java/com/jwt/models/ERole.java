package com.jwt.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//exsiting code like working
//public enum ERole {
//  ROLE_USER,
//  ROLE_MODERATOR,
//  ROLE_ADMIN
//}

public enum ERole {
	  ROLE_USER,
	  ROLE_MODERATOR,
	  ROLE_ADMIN;
	  
	  public static ERole[] getAllValues() {

	      List<ERole> roleEnums = new ArrayList<>(Arrays.asList(ERole.values()));
	      roleEnums.remove(ERole. ROLE_ADMIN);
	      return roleEnums.toArray(new ERole[0]);
	  }
}