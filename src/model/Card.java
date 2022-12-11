package model;

import javax.swing.ImageIcon;

import model.Game.Flipped;

public class Card {
	
	public enum Color {
		BLUE(0), GREEN(1), RED(2), YELLOW(3), WILD(4);
		
		private int color;
		
		Color(int color) {
			this.color = color;
		}

		public int getColor() {
			return color;
		}
		
		public static Color forValue(int color) {
            for (Color c: values()) {
                if (c.getColor() == color) return c;
            }
            return null;
        }
	}
	
	public enum Value {
		
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
		SIX(6), SEVEN(7), EIGHT(8), NINE(9), 
		REVERSE(10), DRAW_TWO(11), SKIP(12),
		WILD(13), WILD_FOUR(14); 
		
		private final int value;
		
		Value (int value) {
            this.value = value;
        }
		
		public int getValue() {
			return value;
		}
		
		public static Value forValue(int value) {
            for (Value v: values()) {
                if (v.getValue() == value) return v;
            }
            return null;
        }
	}
	
	private Color color;
	private final Value value;
	private static final String subPath = "ImageLibrary/CARTE-UNO/small/";
	private Flipped covered;
    private final ImageIcon backFace = new ImageIcon("ImageLibrary/CARTE-UNO/small/RETRO.png");
	private ImageIcon faceCard;
	
	public Card(Color color, final Value value) {
		this.color = color;
		this.value = value;
		this.covered = Flipped.FLIPPED;
		if (covered.getFlipped()) {
		    this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(subPath+this.toString());
	}
	
	public Card(Color color, final Value value, Flipped covered) {
        this.color = color;
        this.value = value;
        this.covered = covered;
        if (this.covered.getFlipped()) {
            this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(subPath+this.toString());
    }
	
	public static Card getCard(Color color, final Value value) {
		return new Card(color, value);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
        this.color = color;
    }

	public Value getValue() {
		return value;
	}
	
	public String toString() {
		return color + "_" + value + ".png";
	}

    public boolean isWild() {
        return this.getColor().equals(Color.WILD);
    }
    
    public boolean isSpecial() {
        return this.getValue().equals(Value.DRAW_TWO) ||
                this.getValue().equals(Value.REVERSE) ||
                 this.getValue().equals(Value.SKIP); 
    }
    
    public boolean isSameValue(Value validValue) {
        return this.getValue().equals(validValue); 
    }

    public ImageIcon getFaceCard() {
        return faceCard;
    }

    public Flipped isCovered() {
        return covered;
    }

    public void setCovered(Flipped flipped) {
        this.covered = flipped;
        if (flipped.getFlipped()) {
            this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(subPath+this.toString());
    }

}
