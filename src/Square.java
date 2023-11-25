import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//pt안넘어옴
public class Square extends JLabel implements MouseListener {
    private int x;
    private int y;
    private final ChessPane chessPane;
    private final Boolean first_click;
    private String pieceType; // 말의 종류를 저장하는 필드


    public void setPos(int row,int col){
        this.y = row;
        this.x = col;
    }
    private int getPosX(){
        return this.x;
    }
    private int getPosY(){
        return this.y;
    }
    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    public String getPieceType() {
        return this.pieceType;
    }
    public void setImage(ImageIcon img,String pieceType) {
        this.setPieceType(pieceType);
        ImageIcon imgcon;
        if (img != null) {
            imgcon = resizeImage(img, 60, 80);
            setIcon(imgcon);
        } else {
            imgcon = null;
            setIcon(null);
        }
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }
    public Square(int y, int x, ChessPane chessPane) {
        this.y = y;
        this.x = x;
        this.chessPane = chessPane;
        this.first_click = true;
        addMouseListener(this);
        setLayout(null);
    }
    private ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight) {
        Image img = originalImage.getImage();
        Image resizedImg = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!(this.getBackground().equals(Color.CYAN))) {
            // 폰의 이동 처리 부분
            if ("black_pawn".equals(pieceType)) {
                int newY = this.y + 1; // 흑색 폰은 아래로 이동

                // 한 칸 앞으로 이동
                if (newY < ChessPane.DIMENSION && chessPane.getSquareAt(newY, this.x).getIcon() == null) {
                    chessPane.setPos(newY, this.x, Color.CYAN);
                }

                // 첫 이동 시 두 칸 앞으로 이동
                if (this.y == 1 && chessPane.getSquareAt(newY + 1, this.x).getIcon() == null) {
                    chessPane.setPos(newY + 1, this.x, Color.CYAN);
                }

                // 대각선 이동 (상대 말 잡기)
                for (int dx = -1; dx <= 1; dx += 2) {
                    int newX = this.x + dx;
                    if (newX >= 0 && newX < ChessPane.DIMENSION && newY < ChessPane.DIMENSION && chessPane.getSquareAt(newY, newX).getIcon() != null) {
                        chessPane.setPos(newY, newX, Color.CYAN);
                    }
                }

                chessPane.setPickPs(new ImageIcon("black_pawn.png"),this.y,this.x);
            }

            else if ("black_king".equals(pieceType)) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue; // 현재 위치 제외
                        int newY = this.y + i;
                        int newX = this.x + j;
                        if (newY >= 0 && newY < ChessPane.DIMENSION && newX >= 0 && newX < ChessPane.DIMENSION) {
                            chessPane.setPos(newY, newX, Color.CYAN);
                        }
                    }
                } chessPane.setPickPs(new ImageIcon("black_king.png"),this.y,this.x);
            } else if ("black_knight".equals(pieceType)) {
                // 가능한 모든 나이트의 이동 위치 계산
                int[][] possibleMoves = {
                        {this.y - 2, this.x + 1}, {this.y - 1, this.x + 2},
                        {this.y + 1, this.x + 2}, {this.y + 2, this.x + 1},
                        {this.y + 2, this.x - 1}, {this.y + 1, this.x - 2},
                        {this.y - 1, this.x - 2}, {this.y - 2, this.x - 1}
                };

                for (int[] move : possibleMoves) {
                    int newY = move[0];
                    int newX = move[1];
                    if (newY >= 0 && newY < ChessPane.DIMENSION && newX >= 0 && newX < ChessPane.DIMENSION) {
                        chessPane.setPos(newY,newX,Color.CYAN);
                    }
                }
                chessPane.setPickPs(new ImageIcon("black_knight.png"),this.y,this.x);

            } else if ("black_bishop".equals(pieceType)) {
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN);

                    // 왼쪽 위 대각선
                    if (this.y - i >= 0 && this.x - i >= 0)
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN);

                    // 왼쪽 아래 대각선
                    if (this.y + i < ChessPane.DIMENSION && this.x - i >= 0)
                        chessPane.setPos(this.y + i, this.x - i, Color.CYAN);

                    // 오른쪽 위 대각선
                    if (this.y - i >= 0 && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y - i, this.x + i, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("black_bishop.png"),this.y,this.x);
            } else if ("black_queen".equals(pieceType)) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    if (i != this.y) { // 현재 위치 제외
                        chessPane.setPos(i, this.x, Color.CYAN);
                    }
                }

                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    if (j != this.x) { // 현재 위치 제외
                        chessPane.setPos(this.y, j, Color.CYAN);
                    }
                }

                // 대각선 방향 이동
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    // 대각선 이동 가능한 모든 위치 체크
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION) {
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN); // 오른쪽 아래
                    }
                    if (this.y - i >= 0 && this.x + i < ChessPane.DIMENSION) {
                        chessPane.setPos(this.y - i, this.x + i, Color.CYAN); // 오른쪽 위
                    }
                    if (this.y + i < ChessPane.DIMENSION && this.x - i >= 0) {
                        chessPane.setPos(this.y + i, this.x - i, Color.CYAN); // 왼쪽 아래
                    }
                    if (this.y - i >= 0 && this.x - i >= 0) {
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN); // 왼쪽 위
                    }
                } chessPane.setPickPs(new ImageIcon("black_queen.png"),this.y,this.x);
            } else if ("black_rook".equals(pieceType)) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    chessPane.setPos(i, this.x, Color.CYAN);
                }
                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    chessPane.setPos(this.y, j, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("black_rook.png"),this.y,this.x);
            } else if ("white_king".equals(pieceType)) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue; // 현재 위치 제외
                        int newY = this.y + i;
                        int newX = this.x + j;
                        if (newY >= 0 && newY < ChessPane.DIMENSION && newX >= 0 && newX < ChessPane.DIMENSION) {
                            chessPane.setPos(newY, newX, Color.CYAN);
                        }
                    }
                }

            } else if ("white_knight".equals(pieceType)) {
                int[][] possibleMoves = {
                        {this.y - 2, this.x + 1}, {this.y - 1, this.x + 2},
                        {this.y + 1, this.x + 2}, {this.y + 2, this.x + 1},
                        {this.y + 2, this.x - 1}, {this.y + 1, this.x - 2},
                        {this.y - 1, this.x - 2}, {this.y - 2, this.x - 1}
                };

                for (int[] move : possibleMoves) {
                    int newY = move[0];
                    int newX = move[1];
                    if (newY >= 0 && newY < ChessPane.DIMENSION && newX >= 0 && newX < ChessPane.DIMENSION) {
                        chessPane.setPos(newY,newX,Color.CYAN);
                    }
                }
                chessPane.setPickPs(new ImageIcon("white_knight.png"),this.y,this.x);


            } else if ("white_bishop".equals(pieceType)) {
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN);

                    // 왼쪽 위 대각선
                    if (this.y - i >= 0 && this.x - i >= 0)
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN);

                    // 왼쪽 아래 대각선
                    if (this.y + i < ChessPane.DIMENSION && this.x - i >= 0)
                        chessPane.setPos(this.y + i, this.x - i, Color.CYAN);

                    // 오른쪽 위 대각선
                    if (this.y - i >= 0 && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y - i, this.x + i, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("white_bishop.png"),this.y,this.x);
            } else if ("white_queen".equals(pieceType)) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    if (i != this.y) { // 현재 위치 제외
                        chessPane.setPos(i, this.x, Color.CYAN);
                    }
                }
                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    if (j != this.x) { // 현재 위치 제외
                        chessPane.setPos(this.y, j, Color.CYAN);
                    }
                }

                // 대각선 방향 이동
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    // 대각선 이동 가능한 모든 위치 체크
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION) {
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN); // 오른쪽 아래
                    }
                    if (this.y - i >= 0 && this.x + i < ChessPane.DIMENSION) {
                        chessPane.setPos(this.y - i, this.x + i, Color.CYAN); // 오른쪽 위
                    }
                    if (this.y + i < ChessPane.DIMENSION && this.x - i >= 0) {
                        chessPane.setPos(this.y + i, this.x - i, Color.CYAN); // 왼쪽 아래
                    }
                    if (this.y - i >= 0 && this.x - i >= 0) {
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN); // 왼쪽 위
                    }
                } chessPane.setPickPs(new ImageIcon("white_bishop.png"),this.y,this.x);
            } else if ("white_rook".equals(pieceType)) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    chessPane.setPos(i, this.x, Color.CYAN);
                }
                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    chessPane.setPos(this.y, j, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("white_rook.png"),this.y,this.x);
            } else if ("white_pawn".equals(pieceType)) {
                int newY = this.y - 1; // 백색 폰은 위로 이동

                // 한 칸 앞으로 이동
                if (newY >= 0 && chessPane.getSquareAt(newY, this.x).getIcon() == null) {
                    chessPane.setPos(newY, this.x, Color.CYAN);
                }

                // 첫 이동 시 두 칸 앞으로 이동
                if (this.y == 6 && chessPane.getSquareAt(newY - 1, this.x).getIcon() == null) {
                    chessPane.setPos(newY - 1, this.x, Color.CYAN);
                }

                // 대각선 이동 (상대 말 잡기)
                for (int dx = -1; dx <= 1; dx += 2) {
                    int newX = this.x + dx;
                    if (newX >= 0 && newX < ChessPane.DIMENSION && newY >= 0 && chessPane.getSquareAt(newY, newX).getIcon() != null) {
                        chessPane.setPos(newY, newX, Color.CYAN);
                    }
                }
                chessPane.setPickPs(new ImageIcon("white_pawn.png"),this.y,this.x);
            }
            else {
                System.out.println(pieceType);
            }
        }
        else if (this.getBackground().equals(Color.CYAN)){
            chessPane.setSquareAt(  this.y,this.x,chessPane.getPickPs(),chessPane.getPickPs().toString().substring(0,chessPane.getPickPs().toString().length()-4));
            chessPane.setSquareAt(this.y -(this.y- chessPane.getPos().y),this.x -(this.x- chessPane.getPos().x),null,null);
            System.out.println("this y : "+ this.y+"this x"+ this.x+"getPickps : "+chessPane.getPickPs()+"pieceType :"+this.pieceType);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessPane.initBG(j,i);
                }
            }
            chessPane.addTurn();
        }
        else{
            System.out.println("dd");
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}