package de.ubercode.teatime;

public class Tea {
	private String name;
	private int drawTime;

	public Tea() {
	}

	public Tea(String name, int drawTime) {
		this.name = name;
		this.drawTime = drawTime;
	}

	public String getName()	{
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDrawTime() {
		return this.drawTime;
	}

	public void setDrawTime(int drawTime) {
		this.drawTime = drawTime;
	}

	public String getLabel() {
		return this.name + ((this.drawTime > 0)
                            ? " [" + String.valueOf(this.drawTime) + "s]" : "");
	}
}
