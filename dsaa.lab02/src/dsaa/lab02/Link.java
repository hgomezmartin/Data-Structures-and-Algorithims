package dsaa.lab02;

public class Link{
	public String ref;
	public Link(String ref) {
		this.ref=ref;
	}
	// in the future there will be more fields
	
	//method that checks if this link is equal to another object
	//(are equals if they have the same reference)
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) { //check if both objects reference the same memory location
	            return true;
	        }
	        if (obj == null || getClass() != obj.getClass()) { //if the object is null or of a different class
	            return false;
	        }
	        Link other = (Link) obj; //cast the object to a link
	        return ref.equals(other.ref); //compare the references of the links
	    }
	
}
