package kaptainwutax.featureutils.structure.generator.piece.stronghold;

import kaptainwutax.seedutils.lcg.rand.JRand;

public class Start extends SpiralStaircase {

	public PieceWeight pieceWeight;
	public PortalRoom portalRoom;

	public Start(JRand rand, int x, int z, int par5) {
		super(0, rand, x, z);

        StructureVillagePieces.Start start = new StructureVillagePieces.Start(par1World.getWorldChunkManager(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, par5);
        this.components.add(start);
        start.buildComponent(start, this.components, par2Random);
        List list1 = start.field_74930_j;
        List list2 = start.field_74932_i;
        int l;

        while (!list1.isEmpty() || !list2.isEmpty())
        {
            StructureComponent structurecomponent;

            if (list1.isEmpty())
            {
                l = par2Random.nextInt(list2.size());
                structurecomponent = (StructureComponent)list2.remove(l);
                structurecomponent.buildComponent(start, this.components, par2Random);
            }
            else
            {
                l = par2Random.nextInt(list1.size());
                structurecomponent = (StructureComponent)list1.remove(l);
                structurecomponent.buildComponent(start, this.components, par2Random);
            }
        }

        this.updateBoundingBox();
        l = 0;
        Iterator iterator = this.components.iterator();

        while (iterator.hasNext())
        {
            StructureComponent structurecomponent1 = (StructureComponent)iterator.next();

            if (!(structurecomponent1 instanceof StructureVillagePieces.Road))
            {
                ++l;
            }
        }

        this.hasMoreThanTwoComponents = l > 2;
	}

}
