package eu.labrush.rescue.IA.behaviour;

import java.util.Collection;
import java.util.HashSet;

import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;


/**
 * @author ducousso
 *
 */
@SuppressWarnings("unused")
public class BossBehaviour implements BotBehaviour, Cloneable{

	Collection<Bloc> blocs ;
	private Bot b;
	double botX, botY, heroX, heroY, vX, vY, newBlocX, newBlocY, pBlocX, pBlocY ;

	public BossBehaviour() {
		super();
	}
	
	public BossBehaviour(HashSet<Bloc> blocs) {
		super();
		this.blocs = blocs;
	}

	@Override
	public void update(Personnage hero) {
		
		 botX = b.getX();
		 botY = b.getY();
		 heroX = hero.getX();
		 heroY = hero.getY();
		 vX = b.getTrajectoire().getVitesse().getX();
		 vY = b.getTrajectoire().getVitesse().getY();
		
		 
		 double blocX, blocY, ouOnEstX, ouOnEstY, lePlusProcheX, lePlusProcheY;
		 int privateTimer = 0;
		
		Bloc ouOnEst = (Bloc) this.blocs.toArray()[0];
		Bloc lePlusProche = (Bloc) this.blocs.toArray()[0];
		
		for(Bloc bloc: this.blocs){ // Trouver sur quel bloc on est
			
			blocX = xProche(bloc);
			blocY = yProche(bloc);
			
			if(Math.pow(Math.abs(blocX-botX) + Math.abs(blocY-botY),2) <= Math.pow(Math.abs(xProche(ouOnEst)-botX) + Math.abs(yProche(ouOnEst)-botY) ,2) ){
				ouOnEst = bloc ;
			}
		}
		
		ouOnEstX = xProche(ouOnEst);
		ouOnEstY = yProche(ouOnEst);		
		
		for(Bloc bloc: this.blocs){ // Trouver le bloc le plus proche
			
			blocX = xProche(bloc);
			blocY = yProche(bloc);
			
			if(Math.pow(Math.abs(blocX-ouOnEstX) + Math.abs(blocY-ouOnEstY),2) < Math.pow(Math.abs(xProche(lePlusProche)-ouOnEstX) + Math.abs(yProche(lePlusProche)-ouOnEstY) ,2) ){
				lePlusProche = bloc ;
			}
			
		}
		
		lePlusProcheX = xProche(lePlusProche);
		lePlusProcheY = yProche(lePlusProche);
		
		if (((Math.pow(heroX - botX, 2) < 1000000) && (Math.pow(heroY - botY, 2) < 1000000))){ //tire
			double angle, alpha;			
			alpha = Math.atan((botX - heroX - hero.getTrajectoire().getVitesse().getX()) / (botY - heroY - hero.getTrajectoire().getVitesse().getY()));
			angle = alpha;
			b.shoot((int) angle);
		}
		
		if (privateTimer == 0){ // petit saut initial
			double pY;
			
			if(b.getTrajectoire().getVitesse().getY() == 0 && b.getTrajectoire().getAcceleration().getY() == 0){
				b.getTrajectoire().getVitesse().setY(600);
				
			
			if (botY >= ouOnEstY + 600){
				b.getTrajectoire().getVitesse().setY(0);
				privateTimer =1;
			}
			}
		}
		
		if (privateTimer == 1){ //se déplace sur la plateforme
			if (heroX < botX && botX >= ouOnEst.getX()){
				b.getTrajectoire().getVitesse().setX(-5);				
			}
			
			else if (heroX > botX && botX <= (ouOnEst.getX()+ouOnEst.getWidth())){
				b.getTrajectoire().getVitesse().setX(5);				
			}
			
			else {
				privateTimer = 2;
				
				Bloc newBloc = lePlusProche;
				newBlocX = lePlusProcheX;
				newBlocY = lePlusProcheY;
				Bloc pBloc = ouOnEst;
				pBlocX = ouOnEstX;
				pBlocY = ouOnEstY;
			}
		}
		
		if (privateTimer == 2){ //saute d'une plateforme à une autre
			double deltaX = lePlusProcheX-botX;
			double deltaY = lePlusProcheY-botY;			
				
				if (newBlocX != ouOnEstX && newBlocY != ouOnEstY + 15){
					b.getTrajectoire().getVitesse().setX(deltaX);
					b.getTrajectoire().getVitesse().setY(deltaY);
				}
				
				else {
					privateTimer = 1;
				}
								
		}		
		
	}
	
	/**
	 * @param collection the blocs used by the fight algorithm
	 */
	
	public void setBlocs(Collection<Bloc> collection) {
		this.blocs = collection;
	}

	@Override
	public void setBot(Bot bot) {
		this.b = bot ;
	} 
	
	private double xProche (Bloc bloc){
		
		double blocX = bloc.getX();
	
		if (Math.abs(blocX - botX) > Math.abs((blocX + bloc.getWidth() )- botX)){
			blocX = blocX + bloc.getWidth();
		}
		
		return blocX;
	}
	
	private double yProche (Bloc bloc){
		
		double blocY = bloc.getY();
		
		if (Math.abs(blocY - botY) > Math.abs((blocY + bloc.getHeight() )- botY)){
			blocY = blocY + bloc.getHeight();
		}
		
		return blocY;
	}
	
}
