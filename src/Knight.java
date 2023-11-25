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
                chessPane.setSquareAt(7, 1, pieceImg,this.color+"_"+this.name);
                chessPane.setSquareAt(7, 6, pieceImg,this.color+"_"+this.name);
                break;
            case black:
                chessPane.setSquareAt(0, 1, pieceImg,this.color+"_"+this.name);
                chessPane.setSquareAt(0, 6, pieceImg,this.color+"_"+this.name);
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
