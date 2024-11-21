package tv;

public class TV {
	private int channel;
	private int volume;
	private boolean power;

	public TV(int i, int j, boolean b) {
		channel = i;
		volume = j;
		power = b;
	}

	public void status() {
		System.out.println("TV[channel=" + channel + ", volume=" + volume + ", power=" + (power ? "on" : "off") + "]");
	}

	public int getChannel() {
		return channel;
	}

	public void channel(int channel) {
		this.channel = (channel > 255 ? 0 : (channel < 1 ? 255 : channel));
	}

	public void channel(boolean up) {
		this.channel = (this.channel + (up ? 0 : 253)) % 255 + 1;
	}

	public int getVolume() {
		return volume;
	}

	public void volume(int volume) {
		this.volume = (volume > 100 ? 0 : volume);
	}
	
	public void volume(boolean up) {
		this.volume = (this.volume + (up ? 1 : 100)) % 101;
	}

	public boolean getPower() {
		return power;
	}

	public void power(boolean power) {
		this.power = power;
	}
}
