interface Display {
    chartType : String;
    isDisabledDisplay : boolean;
    dataSets : [];
    dataPlugins : DataPlugin[];
    displayPlugins : DisplayPlugin[];
    dataShowDropdown : boolean;
    displayShowDropdown : boolean;
}

interface DataPlugin {
    name: string;
}

interface DisplayPlugin {
    name: string;
}

export type { Display, DataPlugin, DisplayPlugin }