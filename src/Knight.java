import javax.swing.*;

// 폰(Pawn) 클래스는 ChessPiece 클래스를 상속합니다.
public class Knight extends ChessPiece {
    private ChessPane chessPane;

    Knight(Cor cor, ChessPane chessPane) {
        this.name = "knight";
        this.color = cor;
        this.pieceImg = new ImageIcon(this.color+"_"+this.name+".png");
        this.chessPane = chessPane;
    }
    protected void initPos(Cor cor) {
        switch (cor) {
            case white:
                chessPane.setSquareAt(7, 1, pieceImg);
                chessPane.setSquareAt(7, 6, pieceImg);
                break;
            case black:
                chessPane.setSquareAt(0, 1, pieceImg);
                chessPane.setSquareAt(0, 6, pieceImg);
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
