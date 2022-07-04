package com.smartprogramming;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class NotepadEditor implements ActionListener
{
    JFrame jf, replace_frame, font_frame;
    JMenuItem neww, open, save, saveas, pagesetup, print, exit;
    JMenuItem cut, copy, paste, replace;
    JMenuItem font_properties, font_color, bg_color;
    JMenuItem help_me;

    JCheckBoxMenuItem word_wrap;

    JComboBox font_size, font_family, font_style;

    JTextArea textarea;

    JTextField jt1, jt2;
    JButton replace_btn, replace_cancel_btn, ok_btn, cancel_btn;

    File file;

    String title="Untitled - Notepad";

    public NotepadEditor()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        jf=new JFrame(title);
        jf.setSize(500,500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JMenuBar menubar=new JMenuBar();

        JMenu file=new JMenu("File");

        neww=new JMenuItem("New");
        neww.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        neww.addActionListener(this);
        file.add(neww);

        open=new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        open.addActionListener(this);
        file.add(open);

        save=new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);

        saveas=new JMenuItem("Save As...");
        saveas.addActionListener(this);
        file.add(saveas);

        file.addSeparator();

        pagesetup=new JMenuItem("Page Setup");
        pagesetup.addActionListener(this);
        file.add(pagesetup);

        print=new JMenuItem("Print");
        print.addActionListener(this);
        file.add(print);

        file.addSeparator();

        exit=new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(exit);

        menubar.add(file);

        JMenu edit=new JMenu("Edit");

        cut=new JMenuItem("Cut");
        cut.addActionListener(this);
        edit.add(cut);

        copy=new JMenuItem("Copy");
        copy.addActionListener(this);
        edit.add(copy);

        paste=new JMenuItem("Paste");
        paste.addActionListener(this);
        edit.add(paste);

        edit.addSeparator();

        replace=new JMenuItem("Replace");
        replace.addActionListener(this);
        edit.add(replace);

        menubar.add(edit);

        JMenu format=new JMenu("Format");

        font_color=new JMenuItem("Font Color");
        font_color.addActionListener(this);
        format.add(font_color);

        bg_color=new JMenuItem("Background Color");
        bg_color.addActionListener(this);
        format.add(bg_color);

        format.addSeparator();

        font_properties=new JMenuItem("Font...");
        font_properties.addActionListener(this);
        format.add(font_properties);

        word_wrap=new JCheckBoxMenuItem("Word Wrap");
        word_wrap.addActionListener(this);
        format.add(word_wrap);

        menubar.add(format);

        JMenu help=new JMenu("Help");

        help_me=new JMenuItem("Help Me");
        help_me.addActionListener(this);
        help.add(help_me);

        menubar.add(help);

        jf.setJMenuBar(menubar);

        textarea=new JTextArea();
        JScrollPane scrollpane=new JScrollPane(textarea);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jf.add(scrollpane);

        ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("editor-img.png")));
        jf.setIconImage(img.getImage());

        jf.setVisible(true);
    }

    public static void main(String[] args)
    {
        new NotepadEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==neww)
        {
            newFile();
        }
        if(e.getSource()==open)
        {
            open();
        }
        if(e.getSource()==save)
        {
            save();
        }
        if(e.getSource()==saveas)
        {
            saveAs();
        }
        if(e.getSource()==pagesetup)
        {
            pageSetup();
        }
        if(e.getSource()==print)
        {
            print();
        }
        if(e.getSource()==exit)
        {
            System.exit(0);
        }
        if(e.getSource()==cut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==replace)
        {
            openReplaceFrame();
        }
        if(e.getSource()==replace_btn)
        {
            replace();
        }
        if(e.getSource()==replace_cancel_btn)
        {
            replace_frame.setVisible(false);
        }
        if(e.getSource()==font_color)
        {
            setFontColor();
        }
        if(e.getSource()==bg_color)
        {
            setBgColor();
        }
        if(e.getSource()==font_properties)
        {
            openFontDialog();
        }
        if(e.getSource()==ok_btn)
        {
            setFontProperties();
        }
        if(e.getSource()==cancel_btn)
        {
            font_frame.setVisible(false);
        }
        if(e.getSource()==word_wrap)
        {
            setWordWrap();
        }
        if(e.getSource()==help_me)
        {
            helpMe();
        }
    }

    void newFile()
    {
        String str=textarea.getText();
        if(!str.equals(""))
        {
            int i=JOptionPane.showConfirmDialog(jf, "Do you want to save ?");
            if(i==0)
            {
                saveAs();

                textarea.setText("");
                setTitle(title);
            }
            else if(i==1)
            {
                textarea.setText("");
            }
        }
    }
    void open()
    {
        JFileChooser fc=new JFileChooser();
        int i=fc.showOpenDialog(jf);
        if(i==0)
        {
            textarea.setText("");

            File f=fc.getSelectedFile();

            try(FileInputStream fis=new FileInputStream(f);)
            {
                int ii;
                while((ii=fis.read()) != -1)
                {
                    textarea.append(String.valueOf((char)ii));
                }
                setTitle(f.getName());
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
        }
    }
    void save()
    {
        if(file != null)
        {
            try(FileOutputStream fos=new FileOutputStream(file);)
            {
                String str=textarea.getText();

                fos.write(str.getBytes());

                setTitle(file.getName());
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
        }
        else
        {
            saveAs();
        }
    }
    void saveAs()
    {
        JFileChooser fc=new JFileChooser();
        int i=fc.showSaveDialog(jf);
        if(i==0)
        {
            file=fc.getSelectedFile();

            try(FileOutputStream fos=new FileOutputStream(file);)
            {
                String str=textarea.getText();

                fos.write(str.getBytes());

                setTitle(file.getName());
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
        }
    }
    void setTitle(String title)
    {
        jf.setTitle(title);
    }

    void pageSetup()
    {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.pageDialog(pj.defaultPage());
    }
    void print()
    {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.printDialog();
    }

    void openReplaceFrame()
    {
        replace_frame=new JFrame("Replace");
        replace_frame.setSize(450, 350);
        replace_frame.setLocationRelativeTo(jf);
        replace_frame.setLayout(null);

        jt1=new JTextField();
        jt1.setBounds(50, 50, 300, 40);
        replace_frame.add(jt1);

        jt2=new JTextField();
        jt2.setBounds(50, 120, 300, 40);
        replace_frame.add(jt2);

        replace_btn=new JButton("Replace All");
        replace_btn.setBounds(70, 190, 100, 50);
        replace_btn.addActionListener(this);
        replace_frame.add(replace_btn);

        replace_cancel_btn=new JButton("Cancel");
        replace_cancel_btn.setBounds(200, 190, 100, 50);
        replace_cancel_btn.addActionListener(this);
        replace_frame.add(replace_cancel_btn);

        replace_frame.setVisible(true);
    }
    void replace()
    {
        String oldChar=jt1.getText();
        String newChar=jt2.getText();

        String text=textarea.getText();
        textarea.setText(text.replace(oldChar, newChar));

        replace_frame.setVisible(false);
    }

    void setFontColor()
    {
        Color c=JColorChooser.showDialog(jf, "Font Color", Color.black);
        textarea.setForeground(c);
    }
    void setBgColor()
    {
        Color c=JColorChooser.showDialog(jf, "Bg Color", Color.white);
        textarea.setBackground(c);
    }
    void openFontDialog()
    {
        font_frame=new JFrame("Font Properties");
        font_frame.setSize(550,350);
        font_frame.setLayout(null);
        font_frame.setLocationRelativeTo(jf);

        String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_family=new JComboBox(fonts);
        font_family.setBounds(50, 50, 100, 40);
        font_frame.add(font_family);

        String[] style={"Plain", "Bold", "Italic"};
        font_style=new JComboBox(style);
        font_style.setBounds(200, 50, 100, 40);
        font_frame.add(font_style);

        String[] size={"10", "14", "16", "18", "22", "26", "30", "35", "40", "45", "50", "55", "60"};
        font_size=new JComboBox(size);
        font_size.setBounds(350, 50, 100, 40);
        font_frame.add(font_size);

        ok_btn=new JButton("Ok");
        ok_btn.setBounds(100, 150, 70, 50);
        ok_btn.addActionListener(this);
        font_frame.add(ok_btn);

        cancel_btn=new JButton("Cancel");
        cancel_btn.setBounds(220, 150, 100, 50);
        cancel_btn.addActionListener(this);
        font_frame.add(cancel_btn);

        font_frame.setVisible(true);
    }

    void setFontProperties()
    {
        String str_font_family=(String)font_family.getSelectedItem();
        String str_font_style=(String)font_style.getSelectedItem();
        String str_font_size=(String)font_size.getSelectedItem();

        int fstyle=0;
        if(str_font_style.equals("Plain"))
        {
            fstyle=Font.PLAIN;
        }
        else if(str_font_style.equals("Bold"))
        {
            fstyle=Font.BOLD;
        }
        else if(str_font_style.equals("Italic"))
        {
            fstyle=Font.ITALIC;
        }

        Font f=new Font(str_font_family, fstyle, Integer.parseInt(str_font_size));
        textarea.setFont(f);

        font_frame.setVisible(false);
    }

    void setWordWrap()
    {
        textarea.setLineWrap(word_wrap.getState());
    }
    void helpMe()
    {
        try
        {
            Desktop.getDesktop().browse(new URL("https://www.google.com/search?q=smart+programming").toURI());
        } catch (Exception e) {}
    }
}