Compte Rendu TP1
Amal Daoud : MPRSCC

Activité 1-1:
1-Interface IDao :
 
•	IDao est une interface de la couche DAO.
•	On a défini la méthode getValue() pour retourner une valeur. 
•	IDao permet de séparer l’interface de son implémentation => cela rend l’application plus flexible et facilite le remplacement de l’implémentation du DAO.
2-Classe DaoIMP :
 
•	On définit la méthode getValue() qui retourne ici 3
3-Interface IMetier :
 
•	IMetier est l’interface de la couche métier.
•	On définit la méthode calcul().
4-Classe MetierIMP :
 
•	Contient la logique métier du l’application 
•	 Injection de dépendance :
o	Injection de dépendance automatique par Spring {@Autowired: indique à Spring qu’il doit effectuer auto l’injection de cette dépendance, private IDao dao} =>représente donc une dépendance de MetierIMP.
o	Injection de dépendance manuelle : avec la méthode setDao(dao).
5-Classe PresentationStatique:
 
•	Représente la couche présentation de notre application.
•	On crée un objet DaoIMP et un objet MetierIMP
•	Le métier a besoin du DAO pour fonctionner.
•	On injecte donc le DAO dans le métier avec : ((MetierIMP) metier).setDao(dao);
6-Resultat:
 
•	Le console affiche 21 comme résultat. 
•	On utilise une interface et l’injection de dépendances pour avoir un couplage faible entre les classes, ce qui rend le code plus flexible, facile à modifier et à maintenir.

Activité 1-2:
1-Classe PresentationDynamique:
 
Étape 1 — Lire le fichier de configuration
Scanner scanner =
    new Scanner(new File("src/main/resources/config.txt"));

String daoClassName = scanner.nextLine();
•	Scanner lit le fichier.
•	daoClassName contient "dao.DaoIMP".
Étape 2 — Charger la classe DAO
Class cDao = Class.forName(daoClassName);
Class.forName() : charge la classe dont le nom est fourni.
Étape 3 — Créer l'objet DAO
IDao dao = (IDao) cDao.newInstance();
Étape 4 —  Le même principe pour MetierIMP.
Étape 5— Injecter le DAO dans le métier
((MetierIMP) metier).setDao(dao);

2-Fichier cofig.txt:
 
•	Le programme lit les noms des classes dans un fichier config.txt et crée les objets dynamiquement.
3-Resultat:
 

•	8- Dans l’activité 1-1, la classe de présentation crée directement les objets avec new, donc elle contrôle leur création.
Dans l’activité 1-2, les noms des classes sont lus à partir du fichier config.txt, puis les objets sont créés dynamiquement à l’aide de Class.forName() et de la réflexion Java.
Cette approche se rapproche davantage du principe d’Inversion de Contrôle (IoC), car la configuration permet de modifier les implémentations utilisées sans modifier directement le code de présentation.
Cependant, l’IoC n’est pas totalement respecté, car la classe de présentation contrôle encore la lecture de la configuration, la création des objets et l’injection des dépendances.
•	9-
Avantages :
•	Flexibilité : on peut changer l'implémentation du DAO dans config.txt sans modifier le code Java.
•	Indépendance : le code métier dépend des interfaces et non des classes concrètes.
•	Pas besoin de Spring : cette méthode utilise les fonctionnalités natives de Java.
Limites :
•	Configuration manuelle : il faut gérer soi-même la création des objets et l'injection des dépendances.
•	Erreurs à l'exécution : un nom de classe incorrect ou une classe inexistante peut provoquer une exception.
•	Code plus complexe : la réflexion demande davantage de code et de connaissances.









Activité 1-3:
1-Classe PresentationAvecSpring:
 
•	Le principe reste le même, mais la configuration des beans est décrite dans un fichier XML.
•	ClassPathXmlApplicationContext("config.xml") charge le fichier XML depuis le classpath.
•	getBean("metier", IMetier.class) récupère le bean nommé metier.
•	metier.calcul() exécute le calcul.
2-Fichier cofig.xml:

 
•	property name="dao" : désigne la propriété dao du métier, correspondant au setter setDao().
•	ref="dao" : indique que cette propriété reçoit le bean dont l'identifiant est dao.
•	La méthode setDao() est obligatoire.
3-Resultat:
 
•	7 :
1. Création manuelle des objets (Activité 1-1)
Le programmeur crée directement les objets à l’aide du mot-clé new et réalise lui-même l’injection des dépendances.
2. Création par réflexion (Class.forName) (Activité 1-2)
Le programme lit les noms des classes dans un fichier de configuration, puis utilise Class.forName() pour charger les classes et créer les objets dynamiquement. Il gère lui-même l’injection des dépendances.
3. Création par le conteneur Spring (Activité 1-3)
Le programmeur déclare les beans et leurs dépendances dans le fichier config.xml. Le conteneur Spring crée les objets et injecte automatiquement les dépendances selon cette configuration.
Activité 1-4:
1-Classe DaoIMP :
 
•	@Repository : Utilisée pour identifier une classe de la couche d'accès aux données.
•	@Autowired : Permet à Spring d'injecter automatiquement une dépendance dans un composant.
2-Classe MetierIMP :

 
•	@Service : Utilisée pour identifier une classe de la couche métier.
3-Classe PresentationAvecAnnotation:
 
Étape 1 — Créer le conteneur Spring
ApplicationContext context =
    new AnnotationConfigApplicationContext(
        "dao", "metier"
    );
•	ApplicationContext représente le conteneur Spring.
•	AnnotationConfigApplicationContext permet de créer un conteneur qui détecte notamment les composants annotés.
•	"dao" et "metier" indiquent les packages à parcourir pour rechercher les composants.
Spring découvre donc DaoIMP et MetierIMP, à condition que le scan des composants fonctionne correctement.
Étape 2 — Récupérer le métier
IMetier metier = context.getBean(IMetier.class);
Cette instruction demande au conteneur Spring un bean de type IMetier.
Spring renvoie ici le bean MetierIMP.
Étape 3 — Appeler la méthode métier

4-Resultat:
 
•	5-Spring détecte automatiquement les classes grâce aux annotations comme @Repository et @Service, puis injecte les dépendances avec @Autowired.
•	6- Avantages :
o	Simplicité : moins de code de configuration à écrire.
o	Injection automatique : Spring crée les objets et gère leurs dépendances.
o	Maintenance facilitée : la configuration est proche du code concerné.
o	Moins de code répétitif : on évite de créer manuellement chaque objet.
•	Cette solution applique le principe d'Inversion de Contrôle, car la création des objets et l'injection des dépendances sont confiées au conteneur Spring plutôt qu'à la classe de présentation.
Lien du GithHub : https://github.com/amal450/TP_1


