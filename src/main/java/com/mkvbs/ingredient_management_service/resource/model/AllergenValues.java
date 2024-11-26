package com.mkvbs.ingredient_management_service.resource.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AllergenValues {

    public static final String GLUTEN = "Zboża zawierające gluten, tj. pszenica (w tym orkisz i pszenica khorasan), żyto, jęczmień, owies lub ich odmiany hybrydowe, a także produkty pochodne, z wyjątkiem:\n" +
                   "a) syropów glukozowych na bazie pszenicy zawierających dekstrozę (1);\n" +
                   "b) maltodekstryn na bazie pszenicy (1);\n" +
                   "c) syropów glukozowych na bazie jęczmienia;\n" +
                   "d) zbóż wykorzystywanych do produkcji destylatów alkoholowych, w tym alkoholu etylowego pochodzenia rolniczego.";
    public static final String SHELLFISH = "Skorupiaki i produkty pochodne.";
    public static final String EGG = "Jaja i produkty pochodne.";
    public static final String FISH = " Ryby i produkty pochodne, z wyjątkiem:\n" +
                 "a) żelatyny rybnej stosowanej jako nośnik preparatów zawierających witaminy lub karotenoidy;\n" +
                 "b) żelatyny rybnej lub karuku stosowanych jako środki klarujące do piwa i wina.";
    public static final String PEANUTS = "Orzeszki ziemne (arachidowe) i produkty pochodne.";
    public static final String SOY = "Soja i produkty pochodne, z wyjątkiem:\n" +
                "a) całkowicie rafinowanego oleju i tłuszczu sojowego (1);\n" +
                "b) mieszaniny naturalnych tokoferoli (E306), naturalnego D-alfa-tokoferolu, naturalnego octanu D-alfa-tokoferolu, naturalnego bursztynianu D-alfa-tokoferolu pochodzenia sojowego;\n" +
                "c) fitosteroli i estrów fitosteroli otrzymanych z olejów roślinnych pochodzenia sojowego;\n" +
                "d) estru stanolu roślinnego produkowanego ze steroli olejów roślinnych pochodzenia sojowego.";
    public static final String MILK = "Mleko i produkty pochodne (łącznie z laktozą), z wyjątkiem:\n" +
                 "a) serwatki wykorzystywanej do produkcji destylatów alkoholowych, w tym alkoholu etylowego pochodzenia rolniczego,\n" +
                 "b) laktitolu.";
    public static final String NUTS = "Orzechy, tj. migdały (Amygdalus communis L.), orzechy laskowe (Corylus avellana), orzechy włoskie (Juglans regia), orzechy nerkowca (Anacardium occidentale), orzeszki pekan (Carya illinoinensis (Wangenh.) K. Koch), orzechy brazylijskie (Bertholletia excelsa), pistacje/orzechy pistacjowe (Pistacia vera), orzechy makadamia lub orzechy Queensland (Macadamia ternifolia), a także produkty pochodne z wyjątkiem orzechów wykorzystywanych do produkcji destylatów alkoholowych, w tym alkoholu etylowego pochodzenia rolniczego.";
    public static final String CELERY = "Seler i produkty pochodne.";
    public static final String MUSTARD = "Gorczyca i produkty pochodne.";
    public static final String SESAME = "Nasiona sezamu i produkty pochodne.";
    public static final String LUPINE = "Łubin i produkty pochodne.";
    public static final String MOLLUSKS = "Mięczaki i produkty pochodne.\n" +
                     "(1) Oraz produkty pochodne, o ile obróbka, jakiej je poddano, najprawdopodobniej nie wpływa na zwiększenie alergenności, ocenionej przez właściwy organ w odniesieniu do produktu, z którego powstały.";
    public static final String NONE = "Brak alergenów";
}
