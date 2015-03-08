package goldengoblet;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Methods {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;	
	@Persistent
	private String methodName;
	@Persistent
	private List<Long> subjectList;
	@Persistent
	private List<Long> verbList;
	@Persistent
	private List<Long> objectsList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String objects) {
		this.methodName = objects;
	}
	public List<Long> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<Long> subjectList) {
		this.subjectList = subjectList;
	}
	public List<Long> getVerbList() {
		return verbList;
	}
	public void setVerbList(List<Long> verbList) {
		this.verbList = verbList;
	}
	public List<Long> getObjectsList() {
		return objectsList;
	}
	public void setObjectsList(List<Long> objectsList) {
		this.objectsList = objectsList;
	}	
	
	
}
