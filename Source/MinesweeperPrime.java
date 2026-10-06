import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class MinesweeperPrime
{   
    JFrame frame=new JFrame("Minesweeper");
    JLabel textLabel=new JLabel();
    JPanel textPanel=new JPanel();
    JPanel boardPanel=new JPanel();
    int tileSize=60;
    int numRows;
    int numCols;
    int numOfMines;
    int boardWidth;
    int boardHeight;
    Minetile[][] board;
    ArrayList<Minetile> mineList;
    int tilesclicked=0;
    boolean gameover=false;
    Random random=new Random();
    MinesweeperPrime(int numOfMines,int numRows,int numCols){
        this.numOfMines=numOfMines;
        this.numRows=numRows;
        this.numCols=numCols;
        boardWidth= numCols*tileSize;
        boardHeight=numRows*tileSize;
        board = new Minetile[numRows][numCols];
        JFramesetup();
        MineInterface();
    }
    public void JFramesetup(){
        frame.setSize(boardWidth,boardHeight+50);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        textLabel.setFont(new Font("Arial",Font.BOLD,25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);
        boardPanel.setLayout(new GridLayout(numRows, numCols));
        frame.add(boardPanel);
    }

    public void MineInterface(){
        for (int r=0;r<numRows;r++)
        {
            for (int c=0;c<numCols;c++)
            {
                Minetile tile=new Minetile(r,c);
                board[r][c]=tile;
                tile.setFont(new Font("Arial Unicode MS",Font.PLAIN,25));
                tile.setBackground(new Color(48, 204, 43));
                tile.setBorder(BorderFactory.createLineBorder(new Color(6, 124, 8), 1));
                tile.addMouseListener(new MouseAdapter()
                {
                public void mousePressed(MouseEvent e){
                        if(gameover==true)
                        {
                            return;
                        }
                        Minetile tile=(Minetile)e.getSource();
                        if(e.getButton()==MouseEvent.BUTTON1){
                            if("".equals(tile.getText())){
                                if(mineList.contains(tile)){
                                    revealMines();
                                }else{
                                    checkMine(tile.r,tile.c);
                                }
                                
                            }
                        }else if(e.getButton()==MouseEvent.BUTTON3){
                            if("".equals(tile.getText())&& tile.isEnabled()){
                                tile.setText("🚩");
                            }
                            else if("🚩".equals(tile.getText())){
                                tile.setText("");
                            }
                        }
                        
                }
                });
                boardPanel.add(tile);
            }
        }
        setMines();
        frame.setVisible(true);
    }
    public void setMines(){
        mineList=new ArrayList<Minetile>();
        int mineLeft = numOfMines;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);

            Minetile tile = board[r][c]; 
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }
    public void revealMines(){
        for(int i=0;i<mineList.size();i++)
        {
            Minetile tile=mineList.get(i);
            tile.setText("💥");
        }
        gameover=true;
        textLabel.setText("Game Over");
    }
    public void checkMine(int r, int c)
    {
        if(r<0||r>=numRows||c<0||c>=numCols){
            return;
        }
        Minetile tile=board[r][c];
        if(!tile.isEnabled()){
            return;
        }
        tile.setEnabled(false);
        tilesclicked+=1;
        int minefound=0;
        minefound+=countMine(r-1,c-1);
        minefound+=countMine(r-1,c);
        minefound+=countMine(r-1,c+1);
        minefound+=countMine(r,c+1);
        minefound+=countMine(r,c-1);
        minefound+=countMine(r+1,c+1);
        minefound+=countMine(r+1,c);
        minefound+=countMine(r+1,c-1);
        if(minefound>0){
            String NumOfMinesAround=(""+minefound);
            tile.setForeground(new Color(255, 255, 255));
            tile.setText(NumOfMinesAround);
            tile.setBackground(new Color(154, 85, 16));
        }
        else
        {
            tile.setText("");
            tile.setBackground(new Color(154, 85, 16));
            checkMine(r-1,c-1);
            checkMine(r-1,c);
            checkMine(r-1,c+1);
            checkMine(r,c+1);
            checkMine(r,c-1);
            checkMine(r+1,c+1);
            checkMine(r+1,c);
            checkMine(r+1,c-1);
        }
        if(tilesclicked==numRows*numCols-mineList.size())
        {
            gameover=true;
            textLabel.setText("You Win,Mines Cleared");
        }
    }
    public int countMine(int r,int c){
        if(r<0||r>=numRows||c<0||c>=numCols){
            return 0;
        }
        if(mineList.contains(board[r][c])){
            return 1;
        }
        return 0;
    }
}
