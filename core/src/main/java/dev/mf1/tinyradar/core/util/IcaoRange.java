package dev.mf1.tinyradar.core.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

@Slf4j
@Getter
public enum IcaoRange {

    ZIMBABWE(0x004000, 0x0043FF, "Zimbabwe", "zw"),
    MOZAMBIQUE(0x006000, 0x006FFF, "Mozambique", "mz"),
    SOUTH_AFRICA(0x008000, 0x00FFFF, "South Africa", "za"),
    EGYPT(0x010000, 0x017FFF, "Egypt", "eg"),
    LYBIA(0x018000, 0x01FFFF, "Libya", "ly"),
    MOROCCO(0x020000, 0x027FFF, "Morocco", "ma"),
    TUNISIA(0x028000, 0x02FFFF, "Tunisia", "tn"),
    BOTSwana(0x030000, 0x0303FF, "Botswana", "bw"),
    BURUNDI(0x032000, 0x032FFF, "Burundi", "bi"),
    CAMEROON(0x034000, 0x034FFF, "Cameroon", "cm"),
    COMOROS(0x035000, 0x0353FF, "Comoros", "km"),
    CONGO(0x036000, 0x036FFF, "Congo", "cg"),
    COTE_DIVOIRE(0x038000, 0x038FFF, "Cote d'Ivoire", "ci"),
    GABON(0x03E000, 0x03EFFF, "Gabon", "ga"),
    ETHIOPIA(0x040000, 0x040FFF, "Ethiopia", "et"),
    EQUATORIAL_GUINEA(0x042000, 0x042FFF, "Equatorial Guinea", "gq"),
    GHANA(0x044000, 0x044FFF, "Ghana", "gh"),
    GUINEA(0x046000, 0x046FFF, "Guinea", "gn"),
    GUINEA_BISSAU(0x048000, 0x0483FF, "Guinea-Bissau", "gw"),
    LESOTHO(0x04A000, 0x04A3FF, "Lesotho", "ls"),
    KENYA(0x04C000, 0x04CFFF, "Kenya", "ke"),
    LIBERIA(0x050000, 0x050FFF, "Liberia", "lr"),
    MADAGASCAR(0x054000, 0x054FFF, "Madagascar", "mg"),
    MALAWI(0x058000, 0x058FFF, "Malawi", "mw"),
    MALDIVES(0x05A000, 0x05A3FF, "Maldives", "mv"),
    MALI(0x05C000, 0x05CFFF, "Mali", "ml"),
    MAURITANIA(0x05E000, 0x05E3FF, "Mauritania", "mr"),
    MAURITIUS(0x060000, 0x0603FF, "Mauritius", "mu"),
    NIGER(0x062000, 0x062FFF, "Niger", "ne"),
    NIGERIA(0x064000, 0x064FFF, "Nigeria", "ng"),
    UGANDA(0x068000, 0x068FFF, "Uganda", "ug"),
    QATAR(0x06A000, 0x06A3FF, "Qatar", "qa"),
    CENTRAL_AFRICAN_REPUBLIC(0x06C000, 0x06CFFF, "Central African Republic", "cf"),
    RWANDA(0x06E000, 0x06EFFF, "Rwanda", "rw"),
    SENEGAL(0x070000, 0x070FFF, "Senegal", "sn"),
    SEYCHELLES(0x074000, 0x0743FF, "Seychelles", "sc"),
    SIERRA_LEONE(0x076000, 0x0763FF, "Sierra Leone", "sl"),
    SOMALIA(0x078000, 0x078FFF, "Somalia", "so"),
    ESWATINI(0x07A000, 0x07A3FF, "Eswatini", "sz"),
    SUDAN(0x07C000, 0x07CFFF, "Sudan", "sd"),
    TANZANIA(0x080000, 0x080FFF, "Tanzania", "tz"),
    CHAD(0x084000, 0x084FFF, "Chad", "td"),
    TOGO(0x088000, 0x088FFF, "Togo", "tg"),
    ZAMBIA(0x08A000, 0x08AFFF, "Zambia", "zm"),
    DR_CONGO(0x08C000, 0x08CFFF, "DR Congo", "cd"),
    ANGOLA(0x090000, 0x090FFF, "Angola", "ao"),
    BENIN(0x094000, 0x0943FF, "Benin", "bj"),
    CABO_VERDE(0x096000, 0x0963FF, "Cabo Verde", "cv"),
    DJIBOUTI(0x098000, 0x0983FF, "Djibouti", "dj"),
    GAMBIA(0x09A000, 0x09AFFF, "Gambia", "gm"),
    BURKINA_FASO(0x09C000, 0x09CFFF, "Burkina Faso", "bf"),
    SAO_TOME_AND_PRINCIPE(0x09E000, 0x09E3FF, "Sao Tome and Principe", "st"),
    ALGERIA(0x0A0000, 0x0A7FFF, "Algeria", "dz"),
    BAHAMAS(0x0A8000, 0x0A8FFF, "Bahamas", "bs"),
    BARBADOS(0x0AA000, 0x0AA3FF, "Barbados", "bb"),
    BELIZE(0x0AB000, 0x0AB3FF, "Belize", "bz"),
    COLOMBIA(0x0AC000, 0x0ACFFF, "Colombia", "co"),
    COSTA_RICA(0x0AE000, 0x0AEFFF, "Costa Rica", "cr"),
    CUBA(0x0B0000, 0x0B0FFF, "Cuba", "cu"),
    EL_SALVADOR(0x0B2000, 0x0B2FFF, "El Salvador", "sv"),
    GUATEMALA(0x0B4000, 0x0B4FFF, "Guatemala", "gt"),
    GUYANA(0x0B6000, 0x0B6FFF, "Guyana", "gy"),
    HAITI(0x0B8000, 0x0B8FFF, "Haiti", "ht"),
    HONDURAS(0x0BA000, 0x0BAFFF, "Honduras", "hn"),
    SAINT_VINCENT_AND_THE_GRENADINES(0x0BC000, 0x0BC3FF, "Saint Vincent and the Grenadines", "vc"),
    JAMAICA(0x0BE000, 0x0BEFFF, "Jamaica", "jm"),
    NICARAGUA(0x0C0000, 0x0C0FFF, "Nicaragua", "ni"),
    PANAMA(0x0C2000, 0x0C2FFF, "Panama", "pa"),
    DOMINICAN_REPUBLIC(0x0C4000, 0x0C4FFF, "Dominican Republic", "do"),
    TRINIDAD_AND_TOBAGO(0x0C6000, 0x0C6FFF, "Trinidad and Tobago", "tt"),
    SURINAME(0x0C8000, 0x0C8FFF, "Suriname", "sr"),
    ANTIGUA_AND_BARBUDA(0x0CA000, 0x0CA3FF, "Antigua and Barbuda", "ag"),
    GRENADA(0x0CC000, 0x0CC3FF, "Grenada", "gd"),
    MEXICO(0x0D0000, 0x0D7FFF, "Mexico", "mx"),
    VENEZUELA(0x0D8000, 0x0DFFFF, "Venezuela", "ve"),
    RUSSIA(0x100000, 0x1FFFFF, "Russia", "ru"),
    NAMIBIA(0x201000, 0x2013FF, "Namibia", "na"),
    ERITREA(0x202000, 0x2023FF, "Eritrea", "er"),
    ITALY(0x300000, 0x33FFFF, "Italy", "it"),
    SPAIN(0x340000, 0x37FFFF, "Spain", "es"),
    FRANCE(0x380000, 0x3BFFFF, "France", "fr"),
    GERMANY(0x3C0000, 0x3FFFFF, "Germany", "de"),

    BERMUDA(0x400000, 0x4001BF, "Bermuda", "bm"),
    CAYMAN_ISLANDS_1(0x4001C0, 0x4001FF, "Cayman Islands", "ky"),
    TURKS_AND_CAICOS_ISLANDS(0x400300, 0x4003FF, "Turks and Caicos Islands", "tc"),
    CAYMAN_ISLANDS_2(0x424135, 0x4241F2, "Cayman Islands", "ky"),
    BERMUDA_2(0x424200, 0x4246FF, "Bermuda", "bm"),
    CAYMAN_ISLANDS_3(0x424700, 0x424899, "Cayman Islands", "ky"),
    ISLE_OF_MAN(0x424B00, 0x424BFF, "Isle of Man", "im"),
    BERMUDA_3(0x43BE00, 0x43BEFF, "Bermuda", "bm"),
    ISLE_OF_MAN_2(0x43E700, 0x43EAFD, "Isle of Man", "im"),
    GUERNSEY(0x43EAFE, 0x43EEFF, "Guernsey", "gg"),

    UNITED_KINGDOM(0x400000, 0x43FFFF, "United Kingdom", "gb"),
    AUSTRIA(0x440000, 0x447FFF, "Austria", "at"),
    BELGIUM(0x448000, 0x44FFFF, "Belgium", "be"),
    BULGARIA(0x450000, 0x457FFF, "Bulgaria", "bg"),
    DENMARK(0x458000, 0x45FFFF, "Denmark", "dk"),
    FINLAND(0x460000, 0x467FFF, "Finland", "fi"),
    GREECE(0x468000, 0x46FFFF, "Greece", "gr"),
    HUNGARY(0x470000, 0x477FFF, "Hungary", "hu"),
    NORWAY(0x478000, 0x47FFFF, "Norway", "no"),
    NETHERLANDS(0x480000, 0x487FFF, "Kingdom of the Netherlands", "nl"),
    POLAND(0x488000, 0x48FFFF, "Poland", "pl"),
    PORTUGAL(0x490000, 0x497FFF, "Portugal", "pt"),
    CZECHIA(0x498000, 0x49FFFF, "Czechia", "cz"),
    ROMANIA(0x4A0000, 0x4A7FFF, "Romania", "ro"),
    SWEDEN(0x4A8000, 0x4AFFFF, "Sweden", "se"),
    SWITZERLAND(0x4B0000, 0x4B7FFF, "Switzerland", "ch"),
    TURKEY(0x4B8000, 0x4BFFFF, "Turkey", "tr"),
    SERBIA(0x4C0000, 0x4C7FFF, "Serbia", "rs"),
    CYPRUS(0x4C8000, 0x4C83FF, "Cyprus", "cy"),
    IRELAND(0x4CA000, 0x4CAFFF, "Ireland", "ie"),
    ICELAND(0x4CC000, 0x4CCFFF, "Iceland", "is"),
    LUXEMBOURG(0x4D0000, 0x4D03FF, "Luxembourg", "lu"),
    MALTA(0x4D2000, 0x4D2FFF, "Malta", "mt"),
    MONACO(0x4D4000, 0x4D43FF, "Monaco", "mc"),
    SAN_MARINO(0x500000, 0x5003FF, "San Marino", "sm"),
    ALBANIA(0x501000, 0x5013FF, "Albania", "al"),
    CROATIA(0x501C00, 0x501FFF, "Croatia", "hr"),
    LATVIA(0x502C00, 0x502FFF, "Latvia", "lv"),
    LITHUANIA(0x503C00, 0x503FFF, "Lithuania", "lt"),
    MOLDOVA(0x504C00, 0x504FFF, "Moldova", "md"),
    SLOVAKIA(0x505C00, 0x505FFF, "Slovakia", "sk"),
    SLOVENIA(0x506C00, 0x506FFF, "Slovenia", "si"),
    UZBEKISTAN(0x507C00, 0x507FFF, "Uzbekistan", "uz"),
    UKRAINE(0x508000, 0x50FFFF, "Ukraine", "ua"),
    BELARUS(0x510000, 0x5103FF, "Belarus", "by"),
    ESTONIA(0x511000, 0x5113FF, "Estonia", "ee"),
    MACEDONIA(0x512000, 0x5123FF, "Macedonia", "mk"),
    BOSNIA_AND_HERZEGOVINA(0x513000, 0x5133FF, "Bosnia and Herzegovina", "ba"),
    GEORGIA(0x514000, 0x5143FF, "Georgia", "ge"),
    TAJIKISTAN(0x515000, 0x5153FF, "Tajikistan", "tj"),
    MONTENEGRO(0x516000, 0x5163FF, "Montenegro", "me"),
    ARMENIA(0x600000, 0x6003FF, "Armenia", "am"),
    AZERBAIJAN(0x600800, 0x600BFF, "Azerbaijan", "az"),
    KYRGYZSTAN(0x601000, 0x6013FF, "Kyrgyzstan", "kg"),
    TURKMENISTAN(0x601800, 0x601BFF, "Turkmenistan", "tm"),
    BHUTAN(0x680000, 0x6803FF, "Bhutan", "bt"),
    MICRONESIA(0x681000, 0x6813FF, "Micronesia, Federated States of", "fm"),
    MONGOLIA(0x682000, 0x6823FF, "Mongolia", "mn"),
    KAZAKHSTAN(0x683000, 0x6833FF, "Kazakhstan", "kz"),
    PALAU(0x684000, 0x6843FF, "Palau", "pw"),
    AFGHANISTAN(0x700000, 0x700FFF, "Afghanistan", "af"),
    BANGLADESH(0x702000, 0x702FFF, "Bangladesh", "bd"),
    MYANMAR(0x704000, 0x704FFF, "Myanmar", "mm"),
    KUWAIT(0x706000, 0x706FFF, "Kuwait", "kw"),
    LAOS(0x708000, 0x708FFF, "Laos", "la"),
    NEPAL(0x70A000, 0x70AFFF, "Nepal", "np"),
    OMAN(0x70C000, 0x70C3FF, "Oman", "om"),
    CAMBODIA(0x70E000, 0x70EFFF, "Cambodia", "kh"),
    SAUDI_ARABIA(0x710000, 0x717FFF, "Saudi Arabia", "sa"),
    SOUTH_KOREA(0x718000, 0x71FFFF, "South Korea", "kr"),
    NORTH_KOREA(0x720000, 0x727FFF, "North Korea", "kp"),
    IRAQ(0x728000, 0x72FFFF, "Iraq", "iq"),
    IRAN(0x730000, 0x737FFF, "Iran", "ir"),
    ISRAEL(0x738000, 0x73FFFF, "Israel", "il"),
    JORDAN(0x740000, 0x747FFF, "Jordan", "jo"),
    LEBANON(0x748000, 0x74FFFF, "Lebanon", "lb"),
    MALAYSIA(0x750000, 0x757FFF, "Malaysia", "my"),
    PHILIPPINES(0x758000, 0x75FFFF, "Philippines", "ph"),
    PAKISTAN(0x760000, 0x767FFF, "Pakistan", "pk"),
    SINGAPORE(0x768000, 0x76FFFF, "Singapore", "sg"),
    SRI_LANKA(0x770000, 0x777FFF, "Sri Lanka", "lk"),
    SYRIA(0x778000, 0x77FFFF, "Syria", "sy"),
    HONG_KONG(0x789000, 0x789FFF, "Hong Kong", "hk"),
    CHINA(0x780000, 0x7BFFFF, "China", "cn"),
    AUSTRALIA(0x7C0000, 0x7FFFFF, "Australia", "au"),
    INDIA(0x800000, 0x83FFFF, "India", "in"),
    JAPAN(0x840000, 0x87FFFF, "Japan", "jp"),
    THAILAND(0x880000, 0x887FFF, "Thailand", "th"),
    VIET_NAM(0x888000, 0x88FFFF, "Viet Nam", "vn"),
    YEMEN(0x890000, 0x890FFF, "Yemen", "ye"),
    BAHRAIN(0x894000, 0x894FFF, "Bahrain", "bh"),
    BRUNEI(0x895000, 0x8953FF, "Brunei", "bn"),
    UNITED_ARAB_EMIRATES(0x896000, 0x896FFF, "United Arab Emirates", "ae"),
    SOLOMON_ISLANDS(0x897000, 0x8973FF, "Solomon Islands", "sb"),
    PAPUA_NEW_GUINEA(0x898000, 0x898FFF, "Papua New Guinea", "pg"),
    TAIWAN(0x899000, 0x8993FF, "Taiwan", "tw"),
    INDONESIA(0x8A0000, 0x8A7FFF, "Indonesia", "id"),
    MARSHALL_ISLANDS(0x900000, 0x9003FF, "Marshall Islands", "mh"),
    COOK_ISLANDS(0x901000, 0x9013FF, "Cook Islands", "sk"),
    SAMOA(0xC89000, 0xC89FFF, "Samoa", "ws"),
    UNITED_STATES(0xA00000, 0xAFFFFF, "United States", "us"),
    CANADA(0xC00000, 0xC3FFFF, "Canada", "ca"),
    NEW_ZEALAND(0xC80000, 0xC87FFF, "New Zealand", "nz"),
    FIJI(0xC88000, 0xC88FFF, "Fiji", "fj"),
    NAURU(0xC8A000, 0xC8A3FF, "Nauru", "nr"),
    SAINT_LUCIA(0xC8C000, 0xC8C3FF, "Saint Lucia", "lc"),
    TONGA(0xC8D000, 0xC8D3FF, "Tonga", "to"),
    KIRIBATI(0xC8E000, 0xC8E3FF, "Kiribati", "ki"),
    VANUATU(0xC90000, 0xC903FF, "Vanuatu", "vu"),
    ARGENTINA(0xE00000, 0xE3FFFF, "Argentina", "ar"),
    BRAZIL(0xE40000, 0xE7FFFF, "Brazil", "br"),
    CHILE(0xE80000, 0xE80FFF, "Chile", "cl"),
    ECUADOR(0xE84000, 0xE84FFF, "Ecuador", "ec"),
    PARAGUAY(0xE88000, 0xE88FFF, "Paraguay", "py"),
    PERU(0xE8C000, 0xE8CFFF, "Peru", "pe"),
    URUGUAY(0xE90000, 0xE90FFF, "Uruguay", "uy"),
    BOLIVIA(0xE94000, 0xE94FFF, "Bolivia", "bo"),

    ICAO_TEMP(0xF00000, 0xF07FFF, "ICAO (temporary)", "xy"),
    ICAO_SPECIAL(0xF09000, 0xF093FF, "ICAO (special use)", "xy");

    private final int start;
    private final int end;
    private final String country;
    private final String countryCode;

    IcaoRange(int start, int end, String country, String countryCode) {
        this.start = start;
        this.end = end;
        this.country = country;
        this.countryCode = countryCode;
    }

    @Nullable
    public static IcaoRange getCountry(String icaoHex) {
        try {
            int code = Integer.parseInt(icaoHex, 16);
            for (IcaoRange range : IcaoRange.values()) {
                if (code >= range.start && code <= range.end) {
                    return range;
                }
            }
        } catch (NumberFormatException e) {
            log.error("", e);
        }

        log.error("Unable to find country code for {}", icaoHex);

        return null;
    }

    public static String getCountryCode(String icaoHex) {
        try {
            int code = Integer.parseInt(icaoHex, 16);
            for (IcaoRange range : IcaoRange.values()) {
                if (code >= range.start && code <= range.end) {
                    return range.countryCode;
                }
            }
        } catch (NumberFormatException e) {
            log.error("", e);
        }

        log.error("Unable to find country code for {}", icaoHex);

        // Fallback value
        return "xy";
    }

}
