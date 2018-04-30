package game;

public class Coordinate{
    int positionX;
    int positionY;

    public Coordinate(int x,int y){
        positionX=x;
        positionY=y;
    }

    public boolean isValidCoordinate(){
    	if ((positionX>=0 && positionX<8) &&
    		(positionY>=0 && positionY<8) )
    		{
    			return true;
    		}
    		return false;
    			
    }
    public int getX(){return positionX;}
    public int getY(){return positionY;}
    public void setX(int x){positionX=x;}
    public void setY(int y){positionY=y;}
    public String toString(){return Integer.toString(positionX)+","+Integer.toString(positionY);}
    
    public boolean equals(Coordinate p)
    {
    	if ((positionX==p.getX()) &&
    		(positionY==p.getY()) )
    	{
    		return true;
    	}
    	return false;
    }
}