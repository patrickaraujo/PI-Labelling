import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author arauj
 *
 */
public class RowByRow_Tests {

	/**
	 * @param args
	 */
	public static int[][] getMatrix(BufferedImage input){
		int height = input.getHeight(); 
		int width = input.getWidth();
		int [][] array = new int[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(input.getRGB(j, i) == Color.BLACK.getRGB())
					array[i][j] = 0;
				else
					array[i][j] = 1;
			}
		}
		return array;
	}
	
	public static int[] toArray(int[][] mO){ // mO: Matriz Original
		int[] retorno = new int[mO.length*mO[0].length];
		for (int i = 0; i < mO.length; i++) 
			for (int j = 0; j < mO[0].length; j++)
				retorno[(i*mO[0].length)+j] = mO[i][j];
		return retorno;
	}
	
	public static int[][] to2dArray(int[] aO, int linhas, int colunas){ // aO: Array Original
		int[][] retorno = new int[linhas][colunas];
		for (int i = 0; i < linhas; i++) 
			for (int j = 0; j < colunas; j++) 
				retorno[i][j] = aO[(j*linhas)+i];
		return retorno;
	}
	
	public static void dump(int[][] x, int linhas, int colunas) throws IOException{
        BufferedWriter w = new BufferedWriter(new FileWriter("dump.txt"));
        for (int i = 0; i < linhas; i++) {
        	for(int j = 0; j < colunas; j++){
                //	String linha = ("["+i+"]["+j+"]: "+x[i][j]+"\t");
        		String linha = (x[i][j]+"\t");
                w.write(linha);
            }
        	w.newLine();
		}
        w.flush();
        w.close();
    }
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedImage imagem = ImageIO.read(new File("landscape.jpg"));
		int linhas = imagem.getWidth();
		int colunas = imagem.getHeight();
		int[][]matrix = getMatrix(imagem);
		
		//	Connected Component
		ConnectComponent cc = new ConnectComponent();
		Dimension d = new Dimension();
		d.height = matrix.length; 
		d.width = matrix.length;
		int x[] = cc.compactLabeling(toArray(matrix), d, true);
		int holes = cc.getMaxLabel();
		System.out.println("holes: "  + holes);
		
		int y[][] = to2dArray(x, cc.getW(), cc.getH());
		
		dump(y, cc.getW(), cc.getH());
		
		BufferedImage bufferedImage = new BufferedImage(linhas, colunas, BufferedImage.TYPE_INT_RGB);
		bufferedImage.setRGB(0, 0, linhas, colunas, x, 0, linhas);
		ImageIO.write(bufferedImage, "PNG", new File("imagemResultante.png"));
		System.out.println("Pronto!");
	}

}
