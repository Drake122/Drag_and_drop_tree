package dyntell.model.person;

public class Person {
	
	
	private int  Id;
	private int Id_Parent;
	private String Name;
	private Double Point;
	
	
	public Person(int id, int id_Parent, String name, Double point) {
		Id = id;
		Id_Parent = id_Parent;
		Name = name;
		Point = point;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getId_Parent() {
		return Id_Parent;
	}
	public void setId_Parent(int id_Parent) {
		Id_Parent = id_Parent;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Double getPoint() {
		return Point;
	}
	public void setPoint(Double point) {
		Point = point;
	}
	
	
	@Override
	public String toString() {
		return "Person [Id=" + Id + ", Id_Parent=" + Id_Parent + ", Name=" + Name + ", Point=" + Point + "]";
	}

}
