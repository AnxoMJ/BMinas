package guiBuscaminas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PantallaPrincipal extends JFrame {
	JPanel panelMinas = new JPanel();
	JPanel panelOpciones = new JPanel();
	GridLayout gridLayout = new GridLayout();
	JButton botReset = new JButton("Generar");
	JSlider sliderMinas = new JSlider(JSlider.HORIZONTAL, 05, 90, 20);
	JLabel vsliderMinas = new JLabel("20 %");
	String[] tamamTablero = { "Pequeno", "Mediano", "Grande", "Extra Grande" };
	JComboBox<String> cBtamTablero = new JComboBox<>(tamamTablero);
	int TamamF = 8;
	int TamamC = 8;
	int nTamamF = 8;
	int nTamamC = 8;
	double ratioMinas = 0.2;
	boolean inicial = true;
	JButton lBotones[][] = new JButton[TamamF][TamamC];
	// instancia de la parte de procesamiento de buscaminas
	Buscamin b = new Buscamin(TamamF, TamamC);
// listeners
	// Listener Botones
	ActionListener listBoton = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boton = (JButton) e.getSource();
			b.descubrirRecursivo((int) boton.getClientProperty("fila"), (int) boton.getClientProperty("columna"));
			actualizarEstado();
		}
	};
	// Listener combo Box
	ActionListener aLcBtamTablero = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			switch ((String) cb.getSelectedItem()) {
			case "Pequeno":
				nTamamF = 8;
				nTamamC = 8;
				break;
			case "Mediano":
				nTamamF = 16;
				nTamamC = 16;
				break;
			case "Grande":
				nTamamF = 16;
				nTamamC = 30;
				break;
			case "Extra Grande":
				nTamamF = 40;
				nTamamC = 60;
				break;
			default:
				break;
			}
			;
		}
	};
	// Listener Slider Minas
	ChangeListener LsliderMinas = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			int valorMinas = ((JSlider) e.getSource()).getValue();
			ratioMinas = ((double) valorMinas / 100);
			vsliderMinas.setText(valorMinas + " %");
		}
	};
	// Reset
	ActionListener aLbotReset = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			TamamF = nTamamF;
			TamamC = nTamamC;
			Principal();
		}
	};

//Utilizando los resultados de la clase Buscamin, actualiza las casillas descubiertas y muestra si se gana o pierde.
	public void actualizarEstado() {
		for (int f = 0; f < b.tamamF; f++) {
			for (int c = 0; c < b.tamamC; c++) {
				if (b.descubierta[f][c])
					if (b.arrayBusca[f][c] == false)
						cambiarIconos(f, c);
					else {
						// Se pierde porque seleccionaste una mina
						desactivarBotones();
						lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/bombdeath.gif"));
						desvelarTablero(false);
						setTitle("Perdiste");
						JOptionPane.showMessageDialog(panelMinas, "Perdiste Wei!!!");
						return;
					}
			}
		}
		// Si se gana
		if (b.elTableroEstaDescubierto()) {
			desactivarBotones();
			desvelarTablero(true);
			setTitle("Ganaste");
			JOptionPane.showMessageDialog(panelMinas, "Ganaste Wei!!!");
		}
	}

//Cambia los iconos de las casillas pasadas
	public void cambiarIconos(int f, int c) {
		int v = b.minasColindantes(f, c);
		switch (v) {
		case 0:
			lBotones[f][c].setIcon(new ImageIcon("iconosBuscamin/open0.gif"));
			break;
		case 1:
			lBotones[f][c].setIcon(new ImageIcon("iconosBuscamin/open1.gif"));
			break;
		case 2:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open2.gif"));
			break;
		case 3:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open3.gif"));
			break;
		case 4:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open4.gif"));
			break;
		case 5:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open5.gif"));
			break;
		case 6:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open6.gif"));
			break;
		case 7:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open7.gif"));
			break;
		case 8:
			lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/open8.gif"));
			break;
		default:
			break;
		}
	}

//Mustra las casillas restantes
	public void desvelarTablero(boolean ganado) {
		for (int f = 0; f < TamamF; f++) {
			for (int c = 0; c < TamamC; c++) {
				if (!b.descubierta[f][c]) {
					if (b.arrayBusca[f][c]) {
						if (!ganado)
							lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/bombrevealed.gif"));
						else
							lBotones[f][c].setIcon(new ImageIcon("./iconosBuscamin/bombflagged.gif"));
					} else {
						cambiarIconos(f, c);
					}
				}
			}
		}
	}

//Desactiva los Botones con dos ForEach
	public void desactivarBotones() {
		for (JButton f[] : lBotones) {
			for (JButton c : f) {
				c.removeActionListener(listBoton);
				}
		}
	}

	public void generarPanelMinas() {
		for (int f = 0; f < TamamF; f++) {
			for (int c = 0; c < TamamC; c++) {
				JButton boton = new JButton();
				boton.setPreferredSize(new Dimension(18, 18));
				boton.setIcon(new ImageIcon("./iconosBuscamin/blank.gif"));
				boton.putClientProperty("fila", f);
				boton.putClientProperty("columna", c);
				boton.addActionListener(listBoton);
				lBotones[f][c] = boton;
				panelMinas.add(boton);
			}
		}
	}

//Se encarga de Generar el panel principal del juego
	public void Principal() {
		if (inicial) {
			b.generarTablero(ratioMinas);
			setLayout(new FlowLayout());
			gridLayout.setRows(TamamF);
			gridLayout.setColumns(TamamC);
			panelMinas.setLayout(gridLayout);
			getContentPane().add(panelMinas);
			generarPanelMinas();
			panelOpciones = new JPanel();
			panelOpciones.setLayout(new FlowLayout());
			add(panelMinas);
// Combo Box
			cBtamTablero.setSelectedIndex(0);
			cBtamTablero.addActionListener(aLcBtamTablero);
// Slider Y Jlabel
			sliderMinas.addChangeListener(LsliderMinas);
			vsliderMinas.setPreferredSize(new Dimension(35, 0));
// Boton Reset
			botReset.addActionListener(aLbotReset);
// Layout y constrains Para panel de opciones
			GridBagLayout lyOpciones = new GridBagLayout();
			panelOpciones.setLayout(lyOpciones);
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.insets = new Insets(0, 0, 15, 0);
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 2;
			constraints.gridheight = 1;
			panelOpciones.add(cBtamTablero, constraints);
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			panelOpciones.add(sliderMinas, constraints);
			constraints.gridx = 1;
			panelOpciones.add(vsliderMinas, constraints);
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth = 2;
			panelOpciones.add(botReset, constraints);
			
// Posicion y configuracion final del marco
			add(panelOpciones);
			setLocation(750, 400);
			setTitle("Buscamin GUI");
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pack();
			inicial = false;
		} else {
			panelMinas.removeAll();
			b = new Buscamin(TamamF, TamamC);
			b.generarTablero(ratioMinas);
			lBotones = new JButton[TamamF][TamamC];
			gridLayout.setRows(TamamF);
			gridLayout.setColumns(TamamC);
			generarPanelMinas();
			pack();
		}
	}

//Ejecuta el programa
	public static void main(String[] args) {
		PantallaPrincipal p = new PantallaPrincipal();
		p.Principal();
	}
}