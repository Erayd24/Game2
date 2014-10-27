package axohEngine2.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[200];
	public boolean up, down, left, right, inventory, action, enter, back, escape, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, zero, one, two, three, four, five, six, seven, eight, nine;
	
	public void update() {
		inventory = keys[KeyEvent.VK_I];
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		enter = keys[KeyEvent.VK_ENTER];
		action = keys[KeyEvent.VK_E];
		back = keys[KeyEvent.VK_BACK_SPACE];
		escape = keys[KeyEvent.VK_ESCAPE];
		
		a = keys[KeyEvent.VK_A];
		b = keys[KeyEvent.VK_B];
		c = keys[KeyEvent.VK_C];
		d = keys[KeyEvent.VK_D];
		e = keys[KeyEvent.VK_E]; 
		f = keys[KeyEvent.VK_F];
		g = keys[KeyEvent.VK_G];
		h = keys[KeyEvent.VK_H];
		i = keys[KeyEvent.VK_I];
		j = keys[KeyEvent.VK_J];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];
		m = keys[KeyEvent.VK_M];
		n = keys[KeyEvent.VK_N]; 
		o = keys[KeyEvent.VK_O];
		p = keys[KeyEvent.VK_P];
		q = keys[KeyEvent.VK_Q];
		r = keys[KeyEvent.VK_R];
		s = keys[KeyEvent.VK_S];
		t = keys[KeyEvent.VK_T];
		u = keys[KeyEvent.VK_U];
		v = keys[KeyEvent.VK_V];
		w = keys[KeyEvent.VK_W];
		x = keys[KeyEvent.VK_X];
		y = keys[KeyEvent.VK_Y];
		z = keys[KeyEvent.VK_Z];
		zero = keys[KeyEvent.VK_0];
		one = keys[KeyEvent.VK_1];
		two = keys[KeyEvent.VK_2];
		three = keys[KeyEvent.VK_3];
		four = keys[KeyEvent.VK_4];
		five = keys[KeyEvent.VK_5];
		six = keys[KeyEvent.VK_6];
		seven = keys[KeyEvent.VK_7];
		eight = keys[KeyEvent.VK_8];
		nine = keys[KeyEvent.VK_9];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) { }
}
