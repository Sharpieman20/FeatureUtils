package kaptainwutax.featureutils.structure.generator;

import kaptainwutax.featureutils.structure.Stronghold;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.*;
import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.BlockBox;
import kaptainwutax.seedutils.util.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StrongholdGenerator {

	private final MCVersion version;

	protected List<PieceWeight<Village.Piece>> pieceWeights = null;
	public Class<? extends Village.Piece> currentPiece = null;
	protected int totalWeight;
	
	public List<Village.Piece> pieceList = null;
	public BlockBox strongholdBox = null;

	protected Predicate<Village.Piece> loopPredicate;
	protected boolean halted;

	public StrongholdGenerator(MCVersion version) {
		this.version = version;
	}

	public MCVersion getVersion() {
		return this.version;
	}

	public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand) {
		return this.generate(worldSeed, chunkX, chunkZ, rand, piece -> true);
	}


    public static int getRandomIntInRange(Random par0Random, int par1, int par2) {
        return par1 >= par2 ? par1 : par0Random.nextInt(par2 - par1 + 1) + par1;
    }

	public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand, Predicate<Stronghold.Piece> shouldContinue) {
		this.halted = false;
		this.loopPredicate = shouldContinue;

		Start startPiece;
		int attemptCount = 0;

		do {
			this.totalWeight = 0;
			this.currentPiece = null;

			this.pieceList = new ArrayList<>();

            rand.setCarverSeed(worldSeed + (long)(attemptCount++), chunkX, chunkZ, this.getVersion());

            if(this.getVersion().isOlderThan(MCVersion.v1_14))rand.nextInt();

			this.pieceWeights = this.getPieceWeights(rand);

			startPiece = new Start(rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);

            // each path does NN checks
            // then PP checks
            // then paths

            // getNextComponentVillagePath
                // build out paths
            // then build out houses

			if(!shouldContinue.test(startPiece))return true;
			this.pieceList.add(startPiece);

			startPiece.populatePieces(this, startPiece,this.pieceList, rand);
			List<Stronghold.Piece> pieces = startPiece.children;

			while(!pieces.isEmpty() && !this.halted) {
				int i = rand.nextInt(pieces.size());
				Stronghold.Piece piece = pieces.remove(i);
				piece.populatePieces(this, startPiece, this.pieceList, rand);
			}
		} while((this.pieceList.isEmpty() || startPiece.portalRoom == null) && !this.halted);

		if(!this.halted) {
			this.strongholdBox = BlockBox.empty();
			this.pieceList.forEach(piece -> this.strongholdBox.encompass(piece.getBoundingBox()));
		}

		return this.halted;
	}

    // for village, 0 weight = no good, not infinite!
	private List<PieceWeight<Stronghold.Piece>> getPieceWeights(Random rand) {
		return new ArrayList<>(Arrays.asList(
				new PieceWeight<>(House4Garden.class, 4, getRandomIntInRange(rand, 2, 4)),
				new PieceWeight<>(Church.class, 20, getRandomIntInRange(rand, 0, 1)),
				new PieceWeight<>(House1.class, 20, getRandomIntInRange(rand, 0, 2)),
				new PieceWeight<>(WoodHut.class, 3, getRandomIntInRange(rand, 2, 5)),
				new PieceWeight<>(Hall.class, 15, getRandomIntInRange(rand, 0, 2)),
				new PieceWeight<>(Field1.class, 3, getRandomIntegerInRange(rand, 1, 4)),
				new PieceWeight<>(Field2.class, 3, getRandomIntegerInRange(rand, 2, 4)),
				new PieceWeight<>(House2.class, 15, getRandomIntegerInRange(rand, 0, 1)),
				new PieceWeight<>(House3.class, 8, getRandomIntegerInRange(rand, 0, 3))
		));
	}


    public getNextComponentVillagePath() {
        // last parameter is getComponentType
        // start componenttype is 0

        // getNextVillageComponent seems to be like inner loop for generate

        if componentType > 3 return null;
    }

	public Stronghold.Piece generateAndAddPiece(Start startPiece, List<Stronghold.Piece> pieces, JRand rand,
	                                            int x, int y, int z, Direction facing, int pieceId) {
		if(pieceId > 50) {
			return null;
		} else if(Math.abs(x - startPiece.getBoundingBox().minX) <= 112 && Math.abs(z - startPiece.getBoundingBox().minZ) <= 112) {
			Stronghold.Piece piece = this.getNextStructurePiece(startPiece, pieces, rand, x, y, z, facing, pieceId + 1);
			
			if(piece != null) {
				pieces.add(piece);

				if(!this.loopPredicate.test(piece)) {
					this.halted = true;
				}

				startPiece.children.add(piece);
			}

			return piece;
		} else {
			return null;
		}
	}

	private Stronghold.Piece getNextStructurePiece(Start startPiece, List<Stronghold.Piece> pieceList, JRand rand,
	                                               int x, int y, int z, Direction facing, int pieceId) {
		if(!this.canAddStructurePieces()) {
			return null;
		} else {
			if(this.currentPiece != null) {
				Stronghold.Piece piece = classToPiece(this.currentPiece, pieceList, rand, x, y, z, facing, pieceId);
				this.currentPiece = null;

				if(piece != null) {
					return piece;
				}
			}

			int int_5 = 0;

			while(int_5 < 5) {
				++int_5;
				int int_6 = rand.nextInt(this.totalWeight);
				Iterator<PieceWeight<Stronghold.Piece>> pieceWeightsIterator = this.pieceWeights.iterator();

				while(pieceWeightsIterator.hasNext()) {
					PieceWeight<Stronghold.Piece> pieceWeight = pieceWeightsIterator.next();
					int_6 -= pieceWeight.pieceWeight;

					if (int_6 < 0) {
						if(!pieceWeight.canSpawnMoreStructuresOfType(pieceId) || pieceWeight == startPiece.pieceWeight) {
							break;
						}

						Stronghold.Piece piece = classToPiece(pieceWeight.pieceClass, pieceList, rand, x, y, z, facing, pieceId);

						if(piece != null) {
							++pieceWeight.instancesSpawned;
							startPiece.pieceWeight = pieceWeight;

							if (!pieceWeight.canSpawnMoreStructures()) {
								pieceWeightsIterator.remove();
							}

							return piece;
						}
					}
				}
			}

			BlockBox boundingBox = SmallCorridor.createBox(pieceList, rand, x, y, z, facing);

			if(boundingBox != null && boundingBox.minY > 1) {
				return new SmallCorridor(pieceId, boundingBox, facing);
			} else {
				return null;
			}
		}
	}

	private static Stronghold.Piece classToPiece(Class<? extends Stronghold.Piece> pieceClass,
	                                             List<Stronghold.Piece> pieceList, JRand rand,
	                                             int x, int y, int z, Direction facing, int pieceId) {
		Stronghold.Piece piece = null;

		if (pieceClass == Corridor.class) {
			piece = Corridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == PrisonHall.class) {
			piece = PrisonHall.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == LeftTurn.class) {
			piece = LeftTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == RightTurn.class) {
			piece = RightTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == SquareRoom.class) {
			piece = SquareRoom.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == Stairs.class) {
			piece = Stairs.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == SpiralStaircase.class) {
			piece = SpiralStaircase.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == FiveWayCrossing.class) {
			piece = FiveWayCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == ChestCorridor.class) {
			piece = ChestCorridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == Library.class) {
			piece = Library.createPiece(pieceList, rand, x, y, z, facing, pieceId);
		} else if(pieceClass == PortalRoom.class) {
			piece = PortalRoom.createPiece(pieceList, x, y, z, facing, pieceId);
		}

		return piece;
	}

	private boolean canAddStructurePieces() {
		boolean flag = false;
		this.totalWeight = 0;

		for(PieceWeight<Stronghold.Piece> pieceWeight: this.pieceWeights) {
			if(pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
				flag = true;
			}

			totalWeight += pieceWeight.pieceWeight;
		}

		return flag;
	}

}