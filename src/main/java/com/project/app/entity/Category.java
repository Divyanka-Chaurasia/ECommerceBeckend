package com.project.app.entity;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="category")
public class Category {
	@Id
	@Column(name="id")
	private String catagoryId;
	@Column(name="catagory_title",length = 60,nullable = false)
	private String title;
	@Column(name="catagory_desc",length = 500)
	private String description;
	@Column(name="catagory_cover_image",length = 500)
	private String coverImage;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
	@JsonIgnore
    List<Product> products;
}
