package Computergrafik.Engine.Planet.Core;

import java.util.ArrayList;

/**
 * class that holds a giant array with planet names. Also contains a method that randomly picks on of the names.
 * @author Simon Weck
 *
 */
public class PlanetNames {
	
	public static ArrayList<String> planetNames;
	
	/**
	 * returns a planet name out of a hughe list and delets it out of the list so no planets can have the same names.
	 * @return
	 * 		-random planet name as String
	 */
	public static String getRandomPlanetName() {
		int randomNumber = (int)(Math.random()*planetNames.size());
		String planetName = planetNames.get(randomNumber);
		planetNames.remove(randomNumber);
		return planetName;
	}
	
	/**
	 * creates list with a lot of names
	 */
	public static void initNames() {
		planetNames=new ArrayList<String>();
		planetNames.add("Sham");
		planetNames.add("Ustruyama");
		planetNames.add("Ugroitis");
		planetNames.add("Avillon");
		planetNames.add("Silmippe");
		planetNames.add("Epra");
		planetNames.add("Oter");
		planetNames.add("Sagutera");
		planetNames.add("Trexigawa");
		planetNames.add("Choth 4X");
		planetNames.add("Drao 4XEM");
		planetNames.add("Sonkolara");
		planetNames.add("Gezounides");
		planetNames.add("Mandilles");
		planetNames.add("Isov");
		planetNames.add("Theliv");
		planetNames.add("Deria");
		planetNames.add("Lioliv");
		planetNames.add("Gevotania");
		planetNames.add("Tromia U3I");
		planetNames.add("Brorth T");
		planetNames.add("Chapheicarro");
		planetNames.add("Zinniemia");
		planetNames.add("Rubiri");
		planetNames.add("Hannurn");
		planetNames.add("Neomia");
		planetNames.add("Utis");
		planetNames.add("Sizazuno");
		planetNames.add("Gnotholea");
		planetNames.add("Borth L9PQ");
		planetNames.add("Croria 90RS");
		planetNames.add("Zolnoulea");
		planetNames.add("Senveyama");
		planetNames.add("Ithoth");
		planetNames.add("Accippe");
		planetNames.add("Eamia");
		planetNames.add("Voipra");
		planetNames.add("Movuhiri");
		planetNames.add("Phokohiri");
		planetNames.add("Crars L92");
		planetNames.add("Crinda Y4Z");
		planetNames.add("Tacralea");
		planetNames.add("Zallunia");
		planetNames.add("Micrion");
		planetNames.add("Lachippe");
		planetNames.add("Vuanov");
		planetNames.add("Tutov");
		planetNames.add("Ziezuno");
		planetNames.add("Noteyama");
		planetNames.add("Sone 7X2");
		planetNames.add("Driri 7N2");
		planetNames.add("Kanvuelia");
		planetNames.add("Xovatov");
		planetNames.add("Custrapus");
		planetNames.add("Zilnore");
		planetNames.add("Soclite");
		planetNames.add("Peuhiri");
		planetNames.add("Gnithutis");
		planetNames.add("Traestea");
		planetNames.add("Solla N07");
		planetNames.add("Badus B02");
		planetNames.add("Yenkopra");
		planetNames.add("Sotheuzuno");
		planetNames.add("Muchurn");
		planetNames.add("Dollillon");
		planetNames.add("Cheyama");
		planetNames.add("Chugawa");
		planetNames.add("Chesocury");
		planetNames.add("Phochezuno");
		planetNames.add("Drarvis VON");
		planetNames.add("Ceshan 2R4O");
		planetNames.add("Xanzunerth");
		planetNames.add("Bagnouruta");
		planetNames.add("Pipharvis");
		planetNames.add("Hennone");
		planetNames.add("Chuliv");
		planetNames.add("Honope");
		planetNames.add("Gneutis");
		planetNames.add("Thuxuruta");
		planetNames.add("Trilia 801");
		planetNames.add("Cronoe BY");
		planetNames.add("Xadraiter");
		planetNames.add("Thelviuvis");
		planetNames.add("Ragrilles");
		planetNames.add("Vonvippe");
		planetNames.add("Thuiturn");
		planetNames.add("Ulia");
		planetNames.add("Breonov");
		planetNames.add("Vezurus");
		planetNames.add("Zypso 1LGU");
		planetNames.add("Cora 1F");
		planetNames.add("Vungiliv");
		planetNames.add("Vulluitune");
		planetNames.add("Sobbilles");
		planetNames.add("Cholreshan");
		planetNames.add("Caiturn");
		planetNames.add("Anerth");
		planetNames.add("Zeinia");
		planetNames.add("Chugeclite");
		planetNames.add("Somia C90");
		planetNames.add("Zarth OO9");
		planetNames.add("Risouter");
		planetNames.add("Honvounope");
		planetNames.add("Bilnone");
		planetNames.add("Cungora");
		planetNames.add("Daitune");
		planetNames.add("Ouhines");
		planetNames.add("Lipulara");
		planetNames.add("Droyanov");
		planetNames.add("Doria 24C4");
		planetNames.add("Strone 0");
		planetNames.add("Vuthecury");
		planetNames.add("Rindolea");
		planetNames.add("Husilles");
		planetNames.add("Osillon");
		planetNames.add("Tionov");
		planetNames.add("Eizuno");
		planetNames.add("Truecarro");
		planetNames.add("Vuretania");
		planetNames.add("Cragua DN7A");
		planetNames.add("Lion AZ3");
		planetNames.add("Necogantu");
		planetNames.add("Pechathea");
		planetNames.add("Liphapus");
		planetNames.add("Sonyria");
		planetNames.add("Xaclite");
		planetNames.add("Kaicarro");
		planetNames.add("Drepawei");
		planetNames.add("Bayenides");
		planetNames.add("Gnilia LN");
		planetNames.add("Llinda HT");
		planetNames.add("Taphaephus");
		planetNames.add("Cubremia");
		planetNames.add("Chomadus");
		planetNames.add("Tathion");
		planetNames.add("Mutis");
		planetNames.add("Hathea");
		planetNames.add("Dobanides");
		planetNames.add("Grakihines");
		planetNames.add("Veon I7XL");
		planetNames.add("Lyke P6");
		planetNames.add("Nankeotov");
		planetNames.add("Zubrienus");
		planetNames.add("Acrosie");
		planetNames.add("Kisosie");
		planetNames.add("Vulea");
		planetNames.add("Hiliv");
		planetNames.add("Gosanope");
		planetNames.add("Ceotis");
		planetNames.add("Gilia 95R");
		planetNames.add("Vilia 6E95");
		planetNames.add("Ustruliv");
		planetNames.add("Cholianov");
		planetNames.add("Kanvillon");
		planetNames.add("Tholmeon");
		planetNames.add("Utania");
		planetNames.add("Guinus");
		planetNames.add("Trelania");
		planetNames.add("Motenus");
		planetNames.add("Chyria 9ZQ");
		planetNames.add("Gnagua BF3A");
		planetNames.add("Geonosis");
		planetNames.add("Mustafar");
		planetNames.add("Kamino");
		planetNames.add("Alderaan");
		planetNames.add("Coruscant");
		planetNames.add("Dagobah");
		planetNames.add("Endor");
		planetNames.add("Kashyyyk");
		planetNames.add("Naboo");
		planetNames.add("Tatooine");
		planetNames.add("Yavin IV");
		planetNames.add("Utapao");
		planetNames.add("Scarif");
		planetNames.add("Duvenerth");
		planetNames.add("Uthaecury");
		planetNames.add("Dechides");
		planetNames.add("Kalvone");
		planetNames.add("Maubos");
		planetNames.add("Haogantu");
		planetNames.add("Cruinope");
		planetNames.add("Crogistea");
		planetNames.add("Noth 8G9");
		planetNames.add("Miri 7");
		planetNames.add("Chesanides");
		planetNames.add("Dunkotis");
		planetNames.add("Thotroria");
		planetNames.add("Gebrypso");
		planetNames.add("Uemia");
		planetNames.add("Nelea");
		planetNames.add("Doyiruta");
		planetNames.add("Vaxohiri");
		planetNames.add("Zomia GX8");
		planetNames.add("Gnilia 319C");
		planetNames.add("Celraoruta");
		planetNames.add("Midaocarro");
		planetNames.add("Athore");
		planetNames.add("Hizorth");
		planetNames.add("Yeavis");
		planetNames.add("Xotania");
		planetNames.add("Dakayama");
		planetNames.add("Gotirus");
		planetNames.add("Strypso 4F");
		planetNames.add("Nore 7NI5");
		planetNames.add("Gegrania");
		planetNames.add("Ganduria");
		planetNames.add("Midrypso");
		planetNames.add("Nutrolla");
		planetNames.add("Maumia");
		planetNames.add("Yaonia");
		planetNames.add("Sipitis");
		planetNames.add("Daonus");
		planetNames.add("Lion LK4G");
		planetNames.add("Dapus 75");
		planetNames.add("Exegol");
	}
	
}
