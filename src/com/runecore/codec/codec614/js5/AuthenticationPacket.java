package com.runecore.codec.codec614.js5;

/**
 * AuthenticationPacket.java
 * @author Harry Andreas<harry@runecore.org>
 *  Feb 8, 2013
 */
public class AuthenticationPacket {

	/**
	 * The revision for authentication
	 */
	private final int revision;

	/**
	 * Construct the packet
	 * 
	 * @param revision
	 */
	public AuthenticationPacket(int revision) {
		this.revision = revision;
	}

	/**
	 * @return the revision
	 */
	public int getRevision() {
		return revision;
	}

}
