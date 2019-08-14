  

import javax.swing.*;

import java.awt.*; 
import java.awt.event.*; 
import java.util.HashMap;
import java.util.Random;
   
   
class PuzzleMod extends JFrame implements ActionListener, ItemListener {
  
	   /**
	 * 
	 */
			private static final long serialVersionUID = 1L;
			private HashMap<Integer, HashMap<String, JButton>> but = new HashMap<>();
			private int blank = 100;
			private int size = 0;
	   private PuzzleMod() {
	   
	   // graphics
		   super("Puzzle");
		   do {
			   try {
				   size = Integer.parseInt(JOptionPane.showInputDialog("Board Size?"));
			   } catch(Exception ignored) {}
		   } while (size < 2);
		   blank = size * size;
		   for (int i = 1; i <= (size * size); i++) {
			   HashMap<String, JButton> hash = new HashMap<>();
			   hash.put((i != (size * size)) ? Integer.toString(i) : " ", new JButton((i != (size * size)) ? Integer.toString(i) : " "));
			   but.put(i, hash);
		   }
		   setSize(1250,1000);
		   setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		   
		   
		   JPanel centreScreen = new JPanel();
		   GridBagLayout flowManager = new GridBagLayout();
		   GridBagConstraints pos = new GridBagConstraints();
		   centreScreen.setLayout(flowManager);
		   
		   for (HashMap<String, JButton> hs : but.values()) {
			  for (JButton b : hs.values()) {
				  b.addActionListener(this);
				  centreScreen.add(b);
				  b.setPreferredSize(new Dimension(900 / size, 900 / size));
				  b.setFont(new Font("Arial", Font.PLAIN, -3 * size + 60));
			  }
		   }
		   int counter_ = 0;
		   for (int y = 1; y <= size; y++) {
			   for (int x = 0; x <= size - 1; x++) {
				   counter_++;
				   for (JButton jb : but.get(counter_).values()) {
					   pos.gridx = x; pos.gridy = y;
					   centreScreen.add(jb, pos);
				   }
			   }
		   }
		   
		   Random ran = new Random();
		   for (int i = 0; i < (size * 1000); i++) {
			   int rand_ = ran.nextInt((size * size)) + 1;
			   
			   if (close(rand_))
				   action(null, rand_);
			   else
				   i--;
		   }
		   
		
		   setContentPane(centreScreen);
		   setVisible(true);
	
		   }
		   
			public void actionPerformed(ActionEvent event) {
					 action(event, 0);
			}
					
			private void action(ActionEvent event, int n) {
				 Object source = null;
				 if (!(event == null)) {
					 source = event.getSource();
				 }
				 String name = null;
				 boolean proper;
				 boolean jbu = false;
				for (int hs : but.keySet()) {
					if (event == null) {
						hs = n;
					}
					for (JButton jb1 : but.get(hs).values()) {
						if ((event == null || jb1 == source) && hs != blank) {
							jbu = true;
						}
					}
					if (jbu) {
						proper = close(hs);
						if (proper) {
							for (String num : but.get(hs).keySet()) {
								name = num;
							}
							for (JButton jb : but.get(hs).values()) {
								JButton putjb = null;
								HashMap<String, JButton> map = new HashMap<>();
								for (JButton b : but.get(blank).values()) {
									putjb = b;
								}
								putjb.setText(name);
								map.put(name, putjb);
								but.put(blank, map);
								jb.setText(" ");
								HashMap<String, JButton> map2 = new HashMap<>();
								map2.put(" ", jb);
								but.put(hs, map2);
								blank = hs;
								jbu = false;
							}
						}
						break;
					}


				}
					  		
			}
			
				   
			
			public static void main (String args[]) {
				
				PuzzleMod puzzle = new PuzzleMod();
			}
			
			private boolean close(int hs) {
				if (hs == blank)
					return false;
				HashMap<String, Integer> map = getGrid(hs);
				int hx = map.get("x");
				int hy = map.get("y");
				map = getGrid(blank);
				int bx = map.get("x");
				int by = map.get("y");
				return hx == bx && (hy == by - 1 || hy == by + 1) || hy == by && (hx == bx - 1 || hx == bx + 1);
			}

			private HashMap<String, Integer> getGrid(int hs) {
				int beg = 1, end = size, x = 0, y = 0;
				for (int i = 1; i <= size; i++) {
					if (hs >= beg && hs <= end) {
						y = i;
						break;
					}
					beg += size;
					end += size;
				}
				end:
				for (int i = 1; i <= size; i++) {
					for (int i2 = i; i2 <= i + (size * (size - 1)); i2 += size) {
						if (hs == i2) {
							x = i;
							break end;
						}
					}
				}
				HashMap<String, Integer> map = new HashMap<>();
				map.put("x", x);
				map.put("y", y);
				return map;
			}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}
}