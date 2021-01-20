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

    // getNextComponentVillagePath
    public void getNextComponentVillagePath(VillageGenerator gen, Piece base, List<Piece> pieces, JRand rand, int x, int y, int z, int type) {

        if (type > 3) {
            return null;
        }
        else if (Math.abs(x - base.getBoundingBox().minX) <= 112 && Math.abs(z - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            StructureBoundingBox structureboundingbox = StructureVillagePieces.Path.func_74933_a(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6);

            if (structureboundingbox != null && structureboundingbox.minY > 10) {
                StructureVillagePieces.Path path = new StructureVillagePieces.Path(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
                int j1 = (path.boundingBox.minX + path.boundingBox.maxX) / 2;
                int k1 = (path.boundingBox.minZ + path.boundingBox.maxZ) / 2;
                int l1 = path.boundingBox.maxX - path.boundingBox.minX;
                int i2 = path.boundingBox.maxZ - path.boundingBox.minZ;
                int j2 = l1 > i2 ? l1 : i2;

                // if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                if (true) {
                    pieces.add(path);
                    base.paths.add(path);
                    return path;
                }
            }

        }
        return null;
    }


    public void getNextVillageStructureComponent() {

        if (type > 50) {
            return null;
        }
        else if (Math.abs(x - base.getBoundingBox().minX) <= 112 && Math.abs(z - par0ComponentVillageStartPiece.getBoundingBox().minZ) <= 112) {
            StructureVillagePieces.Village village = getNextVillageComponent(par0ComponentVillageStartPiece, par1List, par2Random, par3, par4, par5, par6, par7 + 1);


            if (village != null) {
                int j1 = (village.boundingBox.minX + village.boundingBox.maxX) / 2;
                int k1 = (village.boundingBox.minZ + village.boundingBox.maxZ) / 2;
                int l1 = village.boundingBox.maxX - village.boundingBox.minX;
                int i2 = village.boundingBox.maxZ - village.boundingBox.minZ;
                int j2 = l1 > i2 ? l1 : i2;

                // if (par0ComponentVillageStartPiece.getWorldChunkManager().areBiomesViable(j1, k1, j2 / 2 + 4, MapGenVillage.villageSpawnBiomes))
                if (true) {
                    par1List.add(village);
                    base.structures.add(path);
                    return village;
                }
            }

        }
    }

	public static FiveWayCrossing createPiece(List<Stronghold.Piece> pieces, JRand rand, int x, int y, int z, Direction facing, int pieceId) {
		BlockBox box = BlockBox.rotated(x, y, z, -4, -3, 0, 10, 9, 11, facing);
		return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new FiveWayCrossing(pieceId, rand, box, facing) : null;
	}

}
