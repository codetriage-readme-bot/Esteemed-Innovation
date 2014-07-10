package flaxbeard.steamcraft.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelVacuum extends ModelBase {
    public ModelRenderer box;
    public ModelRenderer side;
    public ModelRenderer pipe;
    public ModelRenderer box2;
    public ModelRenderer box3;
    public ModelRenderer box4;
    public ModelRenderer box5;
    public ModelRenderer box6;
    public ModelRenderer pole;
    public ModelRenderer blade1;
    
    
    public ModelVacuum() {
    	this.box = (new ModelRenderer(this, 0, 0)).setTextureSize(64,64);
    	box.addBox(2.0F,0.0F,0.0F, 6, 16, 16);
    	this.pipe = (new ModelRenderer(this, 44, 0)).setTextureSize(64,64);
    	pipe.addBox(0.0F,5.0F,5.0F, 2, 6, 6);
    	this.side = (new ModelRenderer(this, 32, 32)).setTextureSize(64,64);
    	side.addBox(8.0F,1.0F,1.0F,7, 14, 1);
    	this.box2 = (new ModelRenderer(this, 28, 0)).setTextureSize(64,64);
    	box2.addBox(2.1F,0.0F,0.0F, 0, 16, 16);
    	this.pole = (new ModelRenderer(this, 0, 32)).setTextureSize(64, 64);
    	pole.addBox(2.9F,6.5F,6.5F, 5, 3, 3);
    	this.blade1 = (new ModelRenderer(this, 17, 33)).setTextureSize(64, 64);
    	blade1.addBox(6.0F,2.5F,6.5F, 1, 4, 3);
    	this.box3 = (new ModelRenderer(this, 0, 0)).setTextureSize(64,64);
    	box3.addBox(2.0F,1.0F,0.0F, 6, 0, 16);
    	this.box4 = (new ModelRenderer(this, 0, 0)).setTextureSize(64,64);
    	box4.addBox(2.0F,15F,0.0F, 6, 0, 16);
    	this.box5 = (new ModelRenderer(this, 16, 0)).setTextureSize(64,64);
    	box5.addBox(2.0F,0.0F,1.0F, 6, 16, 0);
    	this.box6 = (new ModelRenderer(this, 16, 0)).setTextureSize(64,64);
    	box6.addBox(2.0F,0.0F,15.0F, 6, 16, 0);
    }
    
    public void render()
    {
    	
        this.box.render(0.0625F);
        this.box2.render(0.0625F);
        this.box3.render(0.0625F);
        this.box4.render(0.0625F);
        this.box5.render(0.0625F);
        this.box6.render(0.0625F);
        this.pipe.render(0.0625F);
        this.pipe.render(0.0625F);
    }
    
    public void renderSide()
    {
        this.side.render(0.0625F);
    }
    
    public void renderPole()
    {
        this.pole.render(0.0625F);
    }
    
    public void renderBlade()
    {
        this.blade1.render(0.0625F);
    }
}
