package com.project.app.entity;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {
	public User(String userId) {
		this.userId = userId;
	}
	@Id
	@Column(name = "user_id")
	private String userId;
	@Column(name = "user_name")
	private String name;
	@Column(name = "user_email", unique = true)
	private String email;
	@Column(name = "user_password", length = 50)
	private String password;
	@Column(name = "user_gender", length = 6)
	private String gender;
	@Column(name = "user_about", length = 1000)
	private String about;
	@Column(name = "user_image_name")
	private String imageName;
    @Column(name = "mobile")
	private String mobile;
    @Column(name="confirmPassword")
    private String confirmPassword;
//	@ElementCollection
//	@CollectionTable(name = "userImage", joinColumns = @JoinColumn(name = "user"))
//	@Column(name = "listOfImage")
//	private List<String> listOfImage;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Orders> orders;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles;

	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Cart cart;

}