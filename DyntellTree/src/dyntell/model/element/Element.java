package dyntell.model.element;

import java.util.ArrayList;
import java.util.Collection;


	
	  public class Element {
	         private final String parent;
	         private final String name;
	         private final Collection<Element> children = new ArrayList<Element>();

	         public Element(final String parent, final String name) {
	             super();
	             this.parent = parent;
	             this.name = name;
	         }

	         public String getName() {
	             return name;
	         }

	         public Collection<Element> getChildren() {
	             return children;
	         }

			public String getParent() {
				return parent;
			}

	     }


