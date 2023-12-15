function Computer(processore, disco, ram) {
    this.processore = processore;
    this.disco = disco;
    this.ram = ram;
}

Computer.prototype.infoComputerConsole = function () {
    console.log("Processore: " + this.processore + "\nDisco: " + this.disco + "\nRam: " + this.ram);
}

Computer.prototype.infoComputerDOM = function (id) {
    document.getElementById(id).innerHTML = "<p> Processore: " + this.processore + "</p> <p>Disco: " + this.disco + "</p>Ram: " + this.ram + "</p>"
}

class Computer2 {
    constructor(processore, disco, ram) {
        this.processore = processore;
        this.disco = disco;
        this.ram = ram;
     }

    infoComputerConsole() {
        console.log("Processore: " + this.processore + "\nDisco: " + this.disco + "\nRam: " + this.ram);
    }
    infoComputerDOM(id) {
        document.getElementById(id).innerHTML = "<p>Processore: " + this.processore + "</p> <p>Disco: " + this.disco + "</p> <p>Ram: " + this.ram + "</p>"
    }
}

const mioPC = new Computer("i7", "500GB", "16GB");
mioPC.infoComputerConsole();
mioPC.infoComputerDOM("miopc");

const mioPC2 = new Computer2("i5", "250GB", "16GB");
mioPC2.infoComputerConsole();
mioPC2.infoComputerDOM("miopc2");
