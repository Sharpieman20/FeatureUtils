public class Path {


    public Path() {

        this.size = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());

    }

        /**
        * Initiates construction of the Structure Component picked, at the current Location of StructGen
        */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        boolean flag = false;
        int i;
        StructureComponent structurecomponent1;

        for (i = par3Random.nextInt(5); i < this.size - 8; i += 2 + par3Random.nextInt(5))
        {
            structurecomponent1 = this.getNextComponentNN((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, 0, i);

            if (structurecomponent1 != null)
            {
                i += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
                flag = true;
            }
        }

        for (i = par3Random.nextInt(5); i < this.size - 8; i += 2 + par3Random.nextInt(5))
        {
            structurecomponent1 = this.getNextComponentPP((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, 0, i);

            if (structurecomponent1 != null)
            {
                i += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
                flag = true;
            }
        }

        if (flag && par3Random.nextInt(3) > 0)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                    break;
                case 1:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    break;
                case 2:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                    break;
                case 3:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
        }

        if (flag && par3Random.nextInt(3) > 0)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                    break;
                case 1:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    break;
                case 2:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                    break;
                case 3:
                    StructureVillagePieces.getNextComponentVillagePath((StructureVillagePieces.Start)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
        }
    }
}