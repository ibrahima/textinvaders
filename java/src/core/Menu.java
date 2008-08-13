/**
 * 
 */
package core;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 * @author Ibrahim
 *
 */
public class Menu {
	String[] items;
	int x, y;
	int textheight=-1;
	Font myFont;
	int pos=0;
	public Menu(String options, int x, int y, Font font){
		items=options.split(",");
		this.x=x;
		this.y=y;
		myFont=font;
	}
	public Menu(String[] options, int x, int y, Font font){
		items=options;
		this.x=x;
		this.y=y;
		myFont=font;
	}
	void draw(Graphics2D g){
		Font temp=g.getFont();
		g.setFont(myFont);
		if(textheight==-1){//get the appropriate height for a line of text
			textheight=(int)myFont.getStringBounds("|", g.getFontRenderContext()).getHeight()+3;
		}
		for(int i=0;i<items.length;i++){
			if(pos==i)
				g.drawString("-->"+items[i], x, y+i*textheight);
			else
				g.drawString(items[i], x, y+i*textheight);
		}
		g.setFont(temp);
	}
	public String getPosition(){
		return items[pos];
	}
	public void up(){
		pos--;
		if(pos<0)pos=0;
		System.out.println(getPosition());
	}
	public void down(){
		pos++;
		if(pos>=items.length)pos=items.length-1;
		System.out.println(getPosition());
	}
}
