package axohEngine2.input;

public class Typing extends Keyboard {

	private Keyboard input;
	
	public Typing(Keyboard input) {
		this.input = input;	
	}
	
	public String checkInput() {
		if(input.a) return "a";
		if(input.b) return "b";
		if(input.c) return "c";
		if(input.d) return "d";
		if(input.e) return "e";
		if(input.f) return "f";
		if(input.g) return "g";
		if(input.h) return "h";
		if(input.i) return "i";
		if(input.j) return "j";
		if(input.k) return "k";
		if(input.l) return "l";
		if(input.m) return "m";
		if(input.n) return "n";
		if(input.o) return "o";
		if(input.p) return "p";
		if(input.q) return "q";
		if(input.r) return "r";
		if(input.s) return "s";
		if(input.t) return "t";
		if(input.u) return "u";
		if(input.v) return "v";
		if(input.w) return "w";
		if(input.x) return "x";
		if(input.y) return "y";
		if(input.z) return "z";
		if(input.zero) return "0";
		if(input.one) return "1";
		if(input.two) return "2";
		if(input.three) return "3";
		if(input.four) return "4";
		if(input.five) return "5";
		if(input.six) return "6";
		if(input.seven) return "7";
		if(input.eight) return "8";
		if(input.nine) return "9";
		
		return null;
	}

}
