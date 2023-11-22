import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPane extends JLayeredPane {
    public static final int DIMENSION = 8;
    private static Square[][] grid = new Square[DIMENSION][DIMENSION];
    private ArrayList<Square[][]> turn = new ArrayList<>();
    private static ChessPane boardInstance = new ChessPane();
    private ImageIcon pick_ps;
    private Pos pick_pos;
    public void addTurn(){
        Square[][] sq = grid;
        turn.add(sq);
    }
    public void setPickPs(ImageIcon img, int y, int x){
        this.pick_ps = img;
        this.pick_pos = new Pos(y,x);
    }
    public Pos getPos(){
        return this.pick_pos;
    }
    public ImageIcon getPickPs(){
        return this.pick_ps;
    }
    public static ChessPane getInstance() {
        return boardInstance;
    }

    public Square getSquareAt(int row, int col) {
        return grid[row][col];
    }


    public void setSquareAt(int row, int col, ImageIcon imgcon) {
        grid[row][col].setImage(imgcon);
    }
    public void setPos(int row, int col,Color cor) {
        grid[row][col].setBackground(cor);
    }
    private void initializeSquares() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                grid[i][j] = new Square(i, j,this);
                grid[i][j].addMouseListener(grid[i][j]);
                initBG(j,i);
                grid[i][j].setVisible(true);
            }
        }
    }
    public void initBG(int j,int i){
        grid[i][j].setOpaque(true);
        if ((i + j) % 2 == 0)
            grid[i][j].setBackground(Color.WHITE);
        else
            grid[i][j].setBackground(new Color(0xCCA63D));
    }

    public ChessPane() {
        setLayout(new GridLayout(DIMENSION, DIMENSION));
        initializeSquares();

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                add(grid[i][j]);
            }
        }

        // Pawn 클래스의 초기화를 ChessPane 이후에 진행
        Pawn pw = new Pawn(Cor.black, this);
        Pawn pb = new Pawn(Cor.white, this);
        pw.initPos(pw.color);
        pb.initPos(pb.color);


        Rook r1 = new Rook(Cor.white,this);
        r1.initPos(r1.color);
        Rook r2 = new Rook(Cor.black,this);
        r2.initPos(r2.color);

        Knight kn1= new Knight(Cor.white,this);
        kn1.initPos(kn1.color);
        Knight kn2= new Knight(Cor.black,this);
        kn2.initPos(kn2.color);

        Bishop b1 = new Bishop(Cor.white,this);
        b1.initPos(b1.color);
        Bishop b2 = new Bishop(Cor.black,this);
        b2.initPos(b2.color);

        King k1 = new King(Cor.white,this);
        k1.initPos(k1.color);
        King k2 = new King(Cor.black,this);
        k2.initPos(k2.color);
        Queen q1 = new Queen(Cor.white,this);
        q1.initPos(q1.color);
        Queen q2 = new Queen(Cor.black,this);
        q2.initPos(q2.color);
    }
}