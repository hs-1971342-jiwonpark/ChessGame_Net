import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JLabel implements MouseListener {
    private int x;
    private int y;
    private ImageIcon imgcon;
    private ChessPane chessPane;
    private Boolean move_ps;
    private Boolean first_click;


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
    public void setImage(ImageIcon img) {
        if (img != null) {
            this.imgcon = resizeImage(img, 60, 80);
            setIcon(imgcon);
        } else {
            this.imgcon = null;
            setIcon(null);
        }
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    public Square(int y, int x, ChessPane chessPane) {
        this.y = y;
        this.x = x;
        this.chessPane = chessPane;
        this.move_ps = false;
        this.first_click = true;
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
            if (imgcon.toString().contains("black_pawn")) {
                // 기본적으로 앞으로 한 칸 이동
                int newY = this.y + 1; // 백색 폰은 아래로 이동
                int newX = this.x;

                // 이동 가능 여부 확인 후 표시
                if (newY < ChessPane.DIMENSION) {
                    chessPane.setPos(newY, newX, Color.CYAN);
                    // 대각선 이동 (상대 말 잡기) 가능 여부 확인
                    if (newX > 0) chessPane.setPos(newY, newX - 1, Color.CYAN); // 왼쪽 대각선
                    if (newX < ChessPane.DIMENSION - 1) chessPane.setPos(newY, newX + 1, Color.CYAN); // 오른쪽 대각선
                } chessPane.setPickPs(new ImageIcon("black_pawn.png"),this.y,this.x);
            }
            else if (imgcon.toString().contains("black_king")) {
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
            } else if (imgcon.toString().contains("black_night")) {
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

            } else if (imgcon.toString().contains("black_bishop")) {
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN); // 오른쪽 아래 대각선
                    if (this.y - i >= 0 && this.x - i >= 0)
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN); // 왼쪽 위 대각선
                }
                chessPane.setPickPs(new ImageIcon("black_bishop.png"),this.y,this.x);
            } else if (imgcon.toString().contains("black_queen")) {
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
            } else if (imgcon.toString().contains("black_rook")) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    chessPane.setPos(i, this.x, Color.CYAN);
                }
                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    chessPane.setPos(this.y, j, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("black_bishop.png"),this.y,this.x);
            } else if (imgcon.toString().contains("white_king")) {
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

            } else if (imgcon.toString().contains("white_knight")) {
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


            } else if (imgcon.toString().contains("white_bishop")) {
                for (int i = 1; i < ChessPane.DIMENSION; i++) {
                    if (this.y + i < ChessPane.DIMENSION && this.x + i < ChessPane.DIMENSION)
                        chessPane.setPos(this.y + i, this.x + i, Color.CYAN); // 오른쪽 아래 대각선
                    if (this.y - i >= 0 && this.x - i >= 0)
                        chessPane.setPos(this.y - i, this.x - i, Color.CYAN); // 왼쪽 위 대각선
                }
                chessPane.setPickPs(new ImageIcon("white_bishop.png"),this.y,this.x);
            } else if (imgcon.toString().contains("white_queen")) {
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
            } else if (imgcon.toString().contains("white_rook")) {
                for (int i = 0; i < ChessPane.DIMENSION; i++) {
                    chessPane.setPos(i, this.x, Color.CYAN);
                }
                // 수평 방향 이동
                for (int j = 0; j < ChessPane.DIMENSION; j++) {
                    chessPane.setPos(this.y, j, Color.CYAN);
                }
                chessPane.setPickPs(new ImageIcon("white_rook.png"),this.y,this.x);
            } else if (imgcon.toString().contains("white_pawn")) {
                int newY = this.y - 1; // 흑색 폰은 위로 이동
                int newX = this.x;

                // 이동 가능 여부 확인 후 표시
                if (newY >= 0) {
                    chessPane.setPos(newY, newX, Color.CYAN);
                    // 대각선 이동 (상대 말 잡기) 가능 여부 확인
                    if (newX > 0) chessPane.setPos(newY, newX - 1, Color.CYAN); // 왼쪽 대각선
                    if (newX < ChessPane.DIMENSION - 1) chessPane.setPos(newY, newX + 1, Color.CYAN); // 오른쪽 대각선
                } chessPane.setPickPs(new ImageIcon("white_pawn.png"),this.y,this.x);
            } else {
            }
        }
        else if (this.getBackground().equals(Color.CYAN)){
                movePS(this.y,this.x,chessPane.getPickPs());
                chessPane.setSquareAt(this.y -(this.y- chessPane.getPos().y),this.x -(this.x- chessPane.getPos().x),null);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessPane.initBG(j,i);
                }
            }
            chessPane.addTurn();
        }

    }
    void movePS(int y,int x,ImageIcon img){
        chessPane.setSquareAt(y,x,img);
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