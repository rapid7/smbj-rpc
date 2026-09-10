/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.objects;

/**
 * The SAMPR_DOMAIN_DISPLAY_MACHINE_BUFFER structure holds an array of SAMPR_DOMAIN_DISPLAY_MACHINE elements
 * used to return a list of user accounts through the SamrQueryDisplayInformation family of methods
 * (<a href="https://msdn.microsoft.com/en-us/library/cc245760.aspx">
 * section 3.1.5.3</a>)
 *
 * <pre>
 * typedef struct _SAMPR_DOMAIN_DISPLAY_MACHINE_BUFFER  {
 *   unsigned long EntriesRead;
 *   [size_is(EntriesRead)] PSAMPR_DOMAIN_DISPLAY_MACHINE  Buffer;
 * } SAMPR_DOMAIN_DISPLAY_MACHINE_BUFFER,
 *  *PSAMPR_DOMAIN_DISPLAY_MACHINE_BUFFER;
 * </pre>
 *
 * EntriesRead: The number of elements in Buffer. If zero, Buffer MUST be ignored. If nonzero, Buffer MUST point to at least EntriesRead number of elements.
 * Buffer: An array of SAMPR_DOMAIN_DISPLAY_MACHINE  elements.
 *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/18aa8d70-157e-482e-b2e4-4e6f4dbb76fe">
 *       https://msdn.microsoft.com/en-us/library/cc245639.aspx</a>
 */
public class SAMPRDomainDisplayMachineBuffer extends SAMPREnumerationBuffer<SAMPRDomainDisplayMachine> {
	@Override
	protected SAMPRDomainDisplayMachine initEntity() {
		return new SAMPRDomainDisplayMachine();
	}
}
