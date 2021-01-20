package kaptainwutax.featureutils.structure.generator.piece.stronghold;

import kaptainwutax.featureutils.structure.Stronghold;
import kaptainwutax.featureutils.structure.generator.StrongholdGenerator;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.BlockBox;
import kaptainwutax.seedutils.util.Direction;

import java.util.List;

public class FiveWayCrossing extends Village.Piece {

	private final boolean lowerLeftExists;
	private final boolean upperLeftExists;
	private final boolean lowerRightExists;
	private final boolean upperRightExists;

	public FiveWayCrossing(int pieceId, JRand rand, BlockBox boundingBox, Direction facing) {
		super(pieceId);
		this.setOrientation(facing);
		rand.nextInt(5); //Random entrance.
		this.boundingBox = boundingBox;
		this.lowerLeftExists = rand.nextBoolean();
		this.upperLeftExists = rand.nextBoolean();
		this.lowerRightExists = rand.nextBoolean();
		this.upperRightExists = rand.nextInt(3) > 0;
	}

	@Override
	public void populatePieces(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, JRand rand) {

        this.getNextComponentVillagePath(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, this.getComponentType()));
        this.getNextComponentVillagePath(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, this.getComponentType()));
        this.getNextComponentVillagePath(start, pieces, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, this.getComponentType()));
        this.getNextComponentVillagePath(start, pieces, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, this.getComponentType()));
	}

	public static FiveWayCrossing createPiece(List<Stronghold.Piece> pieces, JRand rand, int x, int y, int z, Direction facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, facing);
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new FiveWayCrossing(pieceId, rand, box, facing) : null;
	}

}
