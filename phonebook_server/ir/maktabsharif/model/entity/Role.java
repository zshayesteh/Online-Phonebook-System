package ir.maktabsharif.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Role {
	@Id
	private String name;
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<Feature> features;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}

	public Role() {
	}

	public Role(String name, Set<Feature> features) {
		this.name = name;
		this.features = features;
	}
	
	public static Role guestRole() {

		Set<Feature> features = new HashSet<Feature>();
		features.add(new Feature("seeContacts"));

		return new Role("guest", features);
	}

	public static Role simpleUserRole() {

		Set<Feature> features = new HashSet<Feature>();

		features.add(new Feature("seeContacts"));
		features.add(new Feature("createContacts"));

		return new Role("simpleUser", features);
	}

	public static Role simpleAdminRole() {

		Set<Feature> features = new HashSet<Feature>();

		features.add(new Feature("seeContacts"));
		features.add(new Feature("createContacts"));
		features.add(new Feature("editContacts"));
		features.add(new Feature("seeLog"));

		return new Role("simpleAdmin", features);
	}

	public static Role mainAdminRole() {

		Set<Feature> features = new HashSet<Feature>();

		features.add(new Feature("seeContacts"));
		features.add(new Feature("createContacts"));
		features.add(new Feature("editContacts"));
		features.add(new Feature("seeLog"));
		features.add(new Feature("editUsers"));
		features.add(new Feature("editRoles"));

		return new Role("mainAdmin", features);
	}
}
