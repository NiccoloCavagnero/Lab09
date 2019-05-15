package it.polito.tdp.borders.model;

public class Border {
	
	private Country st1;
	private Country st2;
	
	public Border(Country st1, Country st2) {
		this.st1 = st1;
		this.st2 = st2;
	}

	public Country getSt1() {
		return st1;
	}

	public void setSt1(Country st1) {
		this.st1 = st1;
	}

	public Country getSt2() {
		return st2;
	}

	public void setSt2(Country st2) {
		this.st2 = st2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((st1 == null) ? 0 : st1.hashCode());
		result = prime * result + ((st2 == null) ? 0 : st2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (st1 == null) {
			if (other.st1 != null)
				return false;
		} else if (!st1.equals(other.st1))
			return false;
		if (st2 == null) {
			if (other.st2 != null)
				return false;
		} else if (!st2.equals(other.st2))
			return false;
		return true;
	}
}
