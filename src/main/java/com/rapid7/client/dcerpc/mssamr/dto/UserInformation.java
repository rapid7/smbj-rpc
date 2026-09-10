
package com.rapid7.client.dcerpc.mssamr.dto;

import com.rapid7.client.dcerpc.mssamr.objects.UserInformationClass;

public interface UserInformation {
	/** 
	 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/6b0dff90-5ac0-429a-93aa-150334adabf6">USER_INFORMATION_CLASS<a/a>
	 * @return UserInformationClass
	 */
    UserInformationClass  getUserInformationClass();
}