import javax.swing.*;

// 폰(Pawn) 클래스는 ChessPiece 클래스를 상속합니다.
public class Rook extends ChessPiece {
    private ChessPane chessPane;

    Rook(Cor cor, ChessPane chessPane) {
        this.name = "rook";
        this.color = cor;
        this.pieceImg = new ImageIcon(this.color+"_"+this.name+".png");
        this.chessPane = chessPane;
    }
    protected void initPos(Cor cor) {
        switch (cor) {
            case white:
                chessPane.setSquareAt(7, 0, pieceImg);
                chessPane.setSquareAt(7, 7, pieceImg);
                break;
            case black:
                chessPane.setSquareAt(0, 0, pieceImg);
                chessPane.setSquareAt(0, 7, pieceImg);
                break;
        }

    }



    @Override
    protected void Move(JPanel chessPan) {

    }

    @Override
    protected Boolean hit() {
        return null;
    }

    @Override
    protected Pos getPos() {
        return null;
    }

    @Override
    protected Pos setPos() {
        return null;
    }
}
