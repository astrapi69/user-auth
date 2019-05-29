/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *  *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *  *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.user.auth.jpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity class {@link Users} is keeping the information for the users from the application.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users extends BaseEntity<Integer> implements Cloneable
{

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;
	/** The attribute active, if true the user account is active. */
	@Column(name = "active")
	private Boolean active;
	/** A Flag that indicates if the user account is locked or not. */
	@Column(name = "locked")
	private Boolean locked;
	/** The hash from the password hashed with sha512. */
	@Column(name = "pw", length = 1024)
	private String pw;
	/** The roles of the user. */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Roles> roles = new HashSet<Roles>();
	/** The salt that is used to compute the hash. */
	@Column(name = "salt", length = 8)
	private String salt;
	/** The user name. */
	@Column(name = "username", length = 256, unique = true)
	private String username;

	/**
	 * Adds a role to the user.
	 * 
	 * @param role
	 *            the role
	 * @return true, if successful
	 */
	public boolean addRole(Roles role)
	{
		return roles.add(role);
	}

	public boolean isActive()
	{
		return getActive();
	}

}
