import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rename extends JFrame implements ActionListener
{
    private String origin,fixed,command;
    private JPanel panel;
    private JTextArea origin_area,fixed_area,command_area;
    private JButton button;
    public Rename(String title)
    {
        super(title);
        this.setBounds(300,100,700,500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,1));
        panel=new JPanel();
        this.add(panel);

        panel.setLayout(new GridLayout(1,7));

        panel.add(new JLabel("原文件名"));
        panel.add(origin_area=new JTextArea());
        panel.add(new JLabel("修改后文件名"));
        panel.add(fixed_area=new JTextArea());
        panel.add(button=new JButton("转换"));
        panel.add(new JLabel("命令行输出"));
        panel.add(command_area=new JTextArea());
        button.addActionListener(this);

        this.setVisible(true);
    }

    public static String[] splitString(String string,int paragraph)
    {
        String str=string;
        String[] strings=new String[paragraph];
        int temp=0;
        for(int i=0; i<str.length(); i++)
        {
            char ch=str.charAt(i);
            if(ch=='\n')
            {
                strings[temp]=str.substring(0,i);
                str=str.substring(i+1);
                temp++;
                i=0;
            }
            if(i==str.length()-1)
                strings[paragraph-1]=str;
            if(temp==paragraph) break;
        }
        return strings;
    }

    public static int paragraphCounter(String str)
    {
        int paragraph=1;
        for(int i=0; i<str.length(); i++)
        {
            String s=str.substring(i,i+1);
            if(s.equals("\n")) paragraph++;
        }
        return paragraph;
    }

    public static void main(String[] args)
    {
        Rename rename=new Rename("重命名生成器");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
            origin=origin_area.getText();
            fixed=fixed_area.getText();
            String[] ori=splitString(origin,paragraphCounter(origin)),
                    fix=splitString(fixed,paragraphCounter(fixed)),
                    com=new String[paragraphCounter(origin)];
            if(ori.length==fix.length)
            {
                for(int i=0; i<ori.length; i++)
                {
                    ori[i]="\""+ori[i]+"\"";
                    fix[i]="\""+fix[i]+"\"";
                    com[i]="ren "+ori[i]+" "+fix[i];
                }
                for(int i=0; i<com.length; i++)
                {
                    if(i==0)
                        command=com[i];
                    else
                        command =command+ "\n" + com[i];
                }
                command_area.setText(command);
            }
            else
                command_area.setText("条目数量不一致！");

        }
    }
}
