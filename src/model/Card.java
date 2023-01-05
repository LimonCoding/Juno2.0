package model;

import javax.swing.ImageIcon;

/**
 * The class Card represents a generic Uno card
 * @author Simone
 */
public class Card {
	/**
	 * The enumeration Color represents possible colors of Uno card
	 * @author Simone
	 */
	public enum Color {
		/** Represents the blue color. */
		BLUE(0), 
		/** Represents the green color. */
		GREEN(1), 
		/** Represents the red color. */
		RED(2), 
		/** Represents the yellow color. */
		YELLOW(3), 
		/** Represents the special card that can be used as any color. */
		WILD(4);
		/**
		 * integer to identify colors of Uno cards
		 */
		private int color;
		/**
		 * Color constructor with color field as parameter
		 * @param color the integer value of color's enumeration
		 */
		Color(int color) {
			this.color = color;
		}
		/**
		 * get the card's color integer value
		 * @return color the integer value of color's enumeration
		 */
		public int getColor() {
			return color;
		}
		/**
		 * get the card's enumeration value of color based on integer value
		 * @param color the integer value of color's enumeration
		 * @return c the enumeration color based on integer value
		 */
		public static Color forValue(int color) {
            for (Color c: values()) {
                if (c.getColor() == color) return c;
            }
            return null;
        }
	}
	/**
	 * The enum Value represents possible values of Uno card
	 * @author Simone
	 */
	public enum Value {
		/** Value representing the card with value 0. */
		ZERO(0),
		/** Value representing the card with value 1. */
		ONE(1), 
		/** Value representing the card with value 2. */
		TWO(2), 
		/** Value representing the card with value 3. */
		THREE(3),
		/** Value representing the card with value 4. */
		FOUR(4),
		/** Value representing the card with value 5. */
		FIVE(5),
		/** Value representing the card with value 6. */
		SIX(6), 
		/** Value representing the card with value 7. */
		SEVEN(7), 
		/** Value representing the card with value 8. */
		EIGHT(8), 
		/** Value representing the card with value 9. */
		NINE(9), 
		/** Value representing the reverse card. */
		REVERSE(10), 
		/** Value representing the draw two card. */
		DRAW_TWO(11), 
		/** Value representing the skip card. */
		SKIP(12),
		/** Value representing the wild card. */
		WILD(13),
		/** Value representing the wild draw four card. */
		WILD_FOUR(14); 
		/**
		 * integer to identify value of Uno cards
		 */
		private final int value;
		/**
		 * Value constructor with value field as parameter
		 * @param value the integer value of value's enumeration
		 */
		Value (int value) {
            this.value = value;
        }
		/**
		 * get the integer card's value
		 * @return color the integer number of card's value enumeration
		 */
		public int getValue() {
			return value;
		}
		/**
		 * get the card's enumeration value based on integer number
		 * @param value the integer card's value enumeration
		 * @return v the enumeration value based on integer
		 */
		public static Value forValue(int value) {
            for (Value v: values()) {
                if (v.getValue() == value) return v;
            }
            return null;
        }
		/**
	     * An enumeration class representing the two possible states of a card being flipped.
	     */
	    public enum Flipped {
	    	/**
	         * Represents a card that is not flipped.
	         */
	        NOT_FLIPPED(true),
	        /**
	         * Represents a card that is flipped.
	         */
	        FLIPPED(false);
	    	/**
	         * Boolean value to identify if card is flipped or not
	         * 
	         * <p>When true indicate that the card is not flipped, 
	         * flipped if false.
	         */
	        private boolean flipped;
	        /**
	         * Constructs a Flipped enumeration value with the specified boolean value.
	         * @param flipped the boolean value of this Flipped enumeration value
	         */
	        Flipped(boolean flipped) {
	            this.flipped = flipped;
	        }
	        /**
	         * Gets the boolean value of this Flipped enumeration value.
	         * @return the boolean value of this Flipped enumeration value
	         */
	        public boolean getFlipped() {
	            return flipped;
	        }
	        /**
	         * Returns the Flipped enumeration value that has the same boolean value as the specified boolean value.
	         * @param flipped the boolean value to match
	         * @return the Flipped enumeration value that has the same boolean value as the specified boolean value
	         */
	        public static Flipped forValue(boolean flipped) {
	            for (Flipped f: values()) {
	                if (f.getFlipped() == flipped) return f;
	            }
	            return NOT_FLIPPED;
	        }
	    }
	}
	/**
     * An enumeration class representing the two possible states of a card being flipped.
     */
    public enum Flipped {
    	/**
         * Represents a card that is not flipped.
         */
        NOT_FLIPPED(true),
        /**
         * Represents a card that is flipped.
         */
        FLIPPED(false);
    	/**
         * Boolean value to identify if card is flipped or not
         * 
         * <p>When true indicate that the card is not flipped, 
         * flipped if false.
         */
        private boolean flipped;
        /**
         * Constructs a Flipped enumeration value with the specified boolean value.
         * @param flipped the boolean value of this Flipped enumeration value
         */
        Flipped(boolean flipped) {
            this.flipped = flipped;
        }
        /**
         * Gets the boolean value of this Flipped enumeration value.
         * @return the boolean value of this Flipped enumeration value
         */
        public boolean getFlipped() {
            return flipped;
        }
        /**
         * Returns the Flipped enumeration value that has the same boolean value as the specified boolean value.
         * @param flipped the boolean value to match
         * @return the Flipped enumeration value that has the same boolean value as the specified boolean value
         */
        public static Flipped forValue(boolean flipped) {
            for (Flipped f: values()) {
                if (f.getFlipped() == flipped) return f;
            }
            return NOT_FLIPPED;
        }
    }
	/**
	 * Color enumeration to identify color card 
	 */
	private Color color;
	/**
	 * Value enumeration to identify value card 
	 */
	private final Value value;
	/**
	 * Flipped enumeration to identify if card is flipped or not 
	 */
	private Flipped covered;
	/**
	 * String constant to identify the sub folder where card images are located
	 */
	private static final String subPath = "/cards/";
	/**
	 * ImageIcon constant of card back image
	 */
	private final ImageIcon backFace = new ImageIcon(getClass().getResource("/cards/RETRO.png"));
	/**
	 * ImageIcon of card image
	 */
	private ImageIcon faceCard;
	/**
	 * Card constructor with color and value field as parameter
	 * @param color the color enumeration of card created
	 * @param value the value enumeration of card created
	 */
	public Card(Color color, final Value value) {
		this.color = color;
		this.value = value;
		this.covered = Flipped.FLIPPED;
		if (covered.getFlipped()) {
		    this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(getClass().getResource(subPath+this.toString()));
	}
	/**
	 * Card constructor with color and value field as parameter
	 * @param color the color enumeration of card created
	 * @param value the value enumeration of card created
	 * @param covered the flipped enumeration of card created
	 */
	public Card(Color color, final Value value, Flipped covered) {
        this.color = color;
        this.value = value;
        this.covered = covered;
        if (this.covered.getFlipped()) {
            this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(getClass().getResource(subPath+this.toString()));
    }
	/**
	 * get a new card with color and value field as parameter
	 * @param color the color enumeration of card created
	 * @param value the value enumeration of card created
	 * @return card the card created
	 */
	public static Card getCard(Color color, final Value value) {
		return new Card(color, value);
	}
	/**
	 * get the card's color
	 * @return color the color enumeration of card
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * color setter method
	 * (reassign the ImageIcon of card image)
	 * @param color the color enumeration of card
	 */
	public void setColor(Color color) {
        this.color = color;
        this.faceCard = new ImageIcon(getClass().getResource(subPath+this.toString()));
    }
	/**
	 * get the card's value
	 * @return value the value enumeration of card
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * check if card have wild value
	 * @return true if card have WILD value
	 */
    public boolean isWild() {
        return this.getValue().equals(Value.WILD);
    }
    /**
	 * check if card have wild four value
	 * @return true if card have WILD_FOUR value
	 */
    public boolean isWildFour() {
        return this.getValue().equals(Value.WILD_FOUR);
    }
    /**
	 * check if card have wild color
	 * @return true if card have WILD color
	 */
    public boolean isColorWild() {
		return this.getColor().equals(Color.WILD);
	}
    /**
     * check if card's color and color of card passed as parameter is equals
     * @param card the card that need to be color compared 
     * @return true if card's color is equals to card's color field
     */
    public boolean isSameColor(Card card) {
        return this.getColor().equals(card.getColor());
    }
    /**
     * check if card's value and value of card passed as parameter is equals
     * @param card the card that need to be value compared 
     * @return true if card's value is equals to card's value field
     */
    public boolean isSameValue(Card card) {
        return this.getValue().equals(card.getValue());
    }
    /**
	 * check if card have a special value
	 * (like draw two, reverse or skip)
	 * @return true if card have DRAW_TWO or REVERSE or SKIP value
	 */
    public boolean isSpecial() {
        return this.getValue().equals(Value.DRAW_TWO) ||
                this.getValue().equals(Value.REVERSE) ||
                 this.getValue().equals(Value.SKIP); 
    }
    /**
	 * get the card's ImageIcon
	 * @return faceCard the image of card
	 */
    public ImageIcon getFaceCard() {
        return faceCard;
    }
    /**
	 * check if card is covered or not
	 * @return covered the Flipped enumeration of card
	 */
    public Flipped isCovered() {
        return covered;
    }
    /**
	 * covered setter method
	 * (reassign the ImageIcon of card image)
	 * @param flipped the Flipped enumeration of card
	 */
    public void setCovered(Flipped flipped) {
        this.covered = flipped;
        if (flipped.getFlipped()) {
            this.faceCard = backFace;
        } else 
            this.faceCard = new ImageIcon(getClass().getResource(subPath+this.toString()));
    }
    /**
	 * @return a string representation of card
	 */
	public String toString() {
		return color + "_" + value + ".png";
	}
}
