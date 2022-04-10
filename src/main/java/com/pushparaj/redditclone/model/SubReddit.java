package com.pushparaj.redditclone.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubReddit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subRedditId;
	@NotBlank(message = "Community name is required")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "subreddit_id",referencedColumnName = "subRedditId")
	private List<Post> posts;
	private Instant createdDate;
	@ManyToOne(fetch = FetchType.LAZY)
	
	private User user;

}
