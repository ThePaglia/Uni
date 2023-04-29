package ex2016.a02b.e1;

import static org.junit.Assert.*;

/**
 * Si consulti la documentazione delle interfacce fornite (Device, Luminous, LuminousDevice e DeviceFactory).
 * Quest'ultima rappresenta una factory per device di tre tipi diversi, che va implementata con una classe
 * DeviceFactoryImpl con costruttore vuoto.
 * I 3 device, rispettivamente devono essere:
 * - device che non falliscono mai
 * - device che falliscono alla N-esima accesione
 * - device con luminosità (comandi dim e brigthen), che falliscono non appena sono accesi e a massima intensità
 * * 
 * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque al raggiungimento 
 * della totalità del punteggio:
 * - la buona progettazione della soluzione, che passi attraverso il pattern template method per fattorizzare le proprietà
 * comuni dei 3 dispositivi da creare.
 * 
 * Si osservi con attenzione il test seguente, che assieme ai commenti delle interfacce date
 * costituisce la definizione del problema da risolvere.
 * 
 * Si tolga il commento al codice del test.
 */

public class Test {
    
    /*
    // Test completo per il primo device da creare
    @org.junit.Test
    public void testDeviceNeverFailing() {
        DeviceFactory factory = new DeviceFactoryImpl();
        Device device = factory.createDeviceNeverFailing();
        // parte off
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        // accendendo e spegnendo tante volte tutto funziona normalmente
        for (int i=0;i<100;i++){
            device.on();
            assertTrue(device.isOn());
            assertTrue(device.isWorking());
            device.off();
            assertFalse(device.isOn());
            assertTrue(device.isWorking());
        }
    }
    
    // Test completo per il secondo device da creare
    @org.junit.Test
    public void testFailingDevice() {
        DeviceFactory factory = new DeviceFactoryImpl();
        Device device = factory.createDeviceFailingAtSwitchOn(3);
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        device.on();
        assertTrue(device.isOn());
        assertTrue(device.isWorking());
        device.off();
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        device.on();
        device.off();
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        // fin qui tutto ok, ora accendo la terza volta..
        device.on();
        // il device non funziona più, e rimane così..
        assertFalse(device.isOn());
        assertFalse(device.isWorking());
        device.on();
        assertFalse(device.isOn());
        assertFalse(device.isWorking());
    }
    
    // Test completo per il terzo device da creare
    @org.junit.Test
    public void testLuminousDevice() {
        DeviceFactory factory = new DeviceFactoryImpl();
        LuminousDevice device = factory.createLuminousDeviceFailingAtMaxIntensity(10);
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        // testo dim e brigthen
        assertEquals(device.getIntesity(),0);
        device.brighten();
        assertEquals(device.getIntesity(),1);
        device.brighten();
        assertEquals(device.getIntesity(),2);
        device.dim();
        assertEquals(device.getIntesity(),1);
        // finché rimango sotto a intensità 10.. tutto funziona sempre
        for (int i=0;i<100;i++){
            device.on();
            assertTrue(device.isOn());
            assertTrue(device.isWorking());
            assertEquals(device.getIntesity(),1);
            device.off();
            assertFalse(device.isOn());
            assertTrue(device.isWorking());
            assertEquals(device.getIntesity(),1);
        }
        // porto l'intensità a 10..
        for (int i=1;i<10;i++){
            device.brighten();
        }
        assertFalse(device.isOn());
        assertTrue(device.isWorking());
        assertEquals(device.getIntesity(),10);
        // ora accendo..
        device.on();
        // il device non funziona più e rimane così..
        assertFalse(device.isOn());
        assertFalse(device.isWorking());
        assertEquals(device.getIntesity(),10);
        device.dim();
        assertFalse(device.isOn());
        assertFalse(device.isWorking());
        assertEquals(device.getIntesity(),9);
        device.on();
        assertFalse(device.isOn());
        assertFalse(device.isWorking());
        assertEquals(device.getIntesity(),9);
        
        // Altro test, fallisce quando da acceso lo porto a maxIntensity
        LuminousDevice device2 = factory.createLuminousDeviceFailingAtMaxIntensity(10);
        device2.on();
        assertTrue(device2.isOn());
        assertTrue(device2.isWorking());
        assertEquals(device2.getIntesity(),0);
        for (int i=0;i<10;i++){
            device2.brighten();
        }
        assertFalse(device2.isOn());
        assertFalse(device2.isWorking());
        assertEquals(device2.getIntesity(),10);
    }
    */
    
}
