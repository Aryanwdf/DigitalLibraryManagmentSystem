package com.dls.interfaces;

import com.dls.entity.Admin;
import com.dls.exception.CustomException;

public interface AdminControllerInterface {
	boolean updatePassword(String password) throws CustomException;

	boolean updateUsername(String username) throws CustomException;

	boolean isRightAuthentication(Admin admin) throws CustomException;

	boolean checkUsername(String username) throws CustomException;

	boolean checkPassword(String password) throws CustomException;

}
