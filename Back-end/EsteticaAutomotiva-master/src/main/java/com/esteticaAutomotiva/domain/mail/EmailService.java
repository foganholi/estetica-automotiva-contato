package com.esteticaAutomotiva.domain.mail;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.esteticaAutomotiva.domain.pessoa.Pessoa;
import com.esteticaAutomotiva.domain.pessoa.PessoaRepository;
import com.esteticaAutomotiva.infra.utils.PasswordUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
	@Autowired
	private JavaMailSender javaMailSender;
	
    @Value("${app.verify.url}")
    private String verifyURL;
    
    @Value("${front.trocarSenhaURL}")
    private String frontTrocarSenhaURL;
    
    @Value("${backend.url}")
    private String backendurl;
    
    @Value("${frontend.url}")
    private String frontend;
	
	public void sendVerificacaoEmail(Pessoa pessoa) throws UnsupportedEncodingException, MessagingException{
		
		String toAddres = pessoa.getEmail();
		String fromAddres = "esteticaAutomoiva@gmail.com";
		String senderName = "EsteticaAutomoiva";
		String subject = "Verifique seu registro";
		
        String content = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "  <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG/>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "  </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <!--<![endif]-->\n" +
                "  <title></title>\n" +
                "\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width: 620px) {\n" +
                "      .u-row {\n" +
                "        width: 600px !important;\n" +
                "      }\n" +
                "      .u-row .u-col {\n" +
                "        vertical-align: top;\n" +
                "      }\n" +
                "      .u-row .u-col-100 {\n" +
                "        width: 600px !important;\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    @media (max-width: 620px) {\n" +
                "      .u-row-container {\n" +
                "        max-width: 100% !important;\n" +
                "        padding-left: 0px !important;\n" +
                "        padding-right: 0px !important;\n" +
                "      }\n" +
                "      .u-row .u-col {\n" +
                "        min-width: 320px !important;\n" +
                "        max-width: 100% !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "      .u-row {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "      .u-col {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "      .u-col>div {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    \n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "    \n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "    \n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "    \n" +
                "    a[x-apple-data-detectors='true'] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: none !important;\n" +
                "    }\n" +
                "    \n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "    \n" +
                "    #u_body a {\n" +
                "      color: #0000ee;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <!--[if !mso]><!-->\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "  <!--<![endif]-->\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table id=\"u_body\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                    <img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 26%;max-width: 150.8px;\"\n" +
                "                                      width=\"150.8\" />\n" +
                "\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong>Muito obrigado por se registrar</strong></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 31px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 28px; line-height: 39.2px;\"><strong><span style=\"line-height: 39.2px; font-size: 28px;\">Verifique o seu e-mail</span></strong>\n" +
                "                                  </span>\n" +
                "                                </p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 22px; line-height: 35.2px;\">Olá [[NAME]], </span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 18px; line-height: 28.8px;\">Você inicou a criação da sua conta mas falta um ultimo passo! Clique no botão abaixo e confirme o seu registro</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <!--[if mso]><style>.v-button {background: transparent !important;}</style><![endif]-->\n" +
                "                              <div align=\"center\">\n" +
                "                                <!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"[[URL]]\" style=\"height:46px; v-text-anchor:middle; width:219px;\" arcsize=\"8.5%\"  stroke=\"f\" fillcolor=\"#ff6600\"><w:anchorlock/><center style=\"color:#FFFFFF;\"><![endif]-->\n" +
                "                                <a href=\"[[URL]]\" target=\"_blank\" class=\"v-button\" style=\"box-sizing: border-box;display: inline-block;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #ff6600; border-radius: 4px;-webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;font-size: 14px;\">\n" +
                "                                  <span style=\"display:block;padding:14px 44px 13px;line-height:120%;\"><span style=\"font-size: 16px; line-height: 19.2px;\"><strong><span style=\"line-height: 19.2px; font-size: 16px;\">Verifique seu email</span></strong>\n" +
                "                                  </span>\n" +
                "                                  </span>\n" +
                "                                </a>\n" +
                "                                <!--[if mso]></center></v:roundrect><![endif]-->\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px 60px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"line-height: 160%; font-size: 14px;\"><span style=\"font-size: 18px; line-height: 28.8px;\">Obrigado!</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #e5eaf5;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #e5eaf5;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">\n" +
                "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->\n" +
                "\n" +
                "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                  <div style=\"height: 100%;width: 100% !important;\">\n" +
                "                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                      <!--<![endif]-->\n" +
                "\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                    </div>\n" +
                "                    <!--<![endif]-->\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "                <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "  <!--[if mso]></div><![endif]-->\n" +
                "  <!--[if IE]></div><![endif]-->\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom(fromAddres, senderName);
        helper.setTo(toAddres);
        helper.setSubject(subject);
        
        content = content.replace("[[NAME]]", pessoa.getNome());

        String verifyURL = this.verifyURL + pessoa.getTokenConfirmacao();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        
        pessoaRepository.save(pessoa);

        javaMailSender.send(message);	
		
	}

    public ResponseEntity<String> confirmarEmail(String token) {
    	
        Pessoa pessoa = validacao(token);
                
        // confirma o email do usuario 
        pessoa.setEmailConfirmado(true);

        pessoa.setDataValidacao(null);

        pessoa.setTokenConfirmacao(null);// 0 o token
     
        pessoaRepository.save(pessoa);
                
        return ResponseEntity.ok("E-mail confirmado com sucesso!");
    }

	public void enviarRecuperarSenha(String email) throws UnsupportedEncodingException, MessagingException {
		
		Pessoa pessoa = pessoaRepository.findByEmailOrLoginWithConfirmedEmail(email)
		    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado ou email não confirmado"));
		
		pessoa.setDataValidacao(LocalDateTime.now());
		
	    pessoa.gerarTokenConfirmacao();
	    pessoaRepository.save(pessoa);
		
		String toAddres = pessoa.getEmail();
		String fromAddres = "esteticaAutomoiva@gmail.com";
		String senderName = "EsteticaAutomotiva";
		String subject = "Atualizar senha";
		
		String logo = backendurl+"/publico/logo";
		
		String content = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\r\n"
				+ "\r\n"
				+ "<head>\r\n"
				+ "  <!--[if gte mso 9]>\r\n"
				+ "  <xml>\r\n"
				+ "    <o:OfficeDocumentSettings>\r\n"
				+ "      <o:AllowPNG/>\r\n"
				+ "      <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
				+ "    </o:OfficeDocumentSettings>\r\n"
				+ "  </xml>\r\n"
				+ "  <![endif]-->\r\n"
				+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "  <meta name=\"x-apple-disable-message-reformatting\">\r\n"
				+ "  <!--[if !mso]><!-->\r\n"
				+ "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "  <!--<![endif]-->\r\n"
				+ "  <title>Atualização de Senha - MEDMAIS</title>\r\n"
				+ "\r\n"
				+ "  <style type=\"text/css\">\r\n"
				+ "    @media only screen and (min-width: 620px) {\r\n"
				+ "      .u-row {\r\n"
				+ "        width: 600px !important;\r\n"
				+ "      }\r\n"
				+ "      .u-row .u-col {\r\n"
				+ "        vertical-align: top;\r\n"
				+ "      }\r\n"
				+ "      .u-row .u-col-100 {\r\n"
				+ "        width: 600px !important;\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    @media (max-width: 620px) {\r\n"
				+ "      .u-row-container {\r\n"
				+ "        max-width: 100% !important;\r\n"
				+ "        padding-left: 0px !important;\r\n"
				+ "        padding-right: 0px !important;\r\n"
				+ "      }\r\n"
				+ "      .u-row .u-col {\r\n"
				+ "        min-width: 320px !important;\r\n"
				+ "        max-width: 100% !important;\r\n"
				+ "        display: block !important;\r\n"
				+ "      }\r\n"
				+ "      .u-row {\r\n"
				+ "        width: 100% !important;\r\n"
				+ "      }\r\n"
				+ "      .u-col {\r\n"
				+ "        width: 100% !important;\r\n"
				+ "      }\r\n"
				+ "      .u-col>div {\r\n"
				+ "        margin: 0 auto;\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    body {\r\n"
				+ "      margin: 0;\r\n"
				+ "      padding: 0;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    table,\r\n"
				+ "    tr,\r\n"
				+ "    td {\r\n"
				+ "      vertical-align: top;\r\n"
				+ "      border-collapse: collapse;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    p {\r\n"
				+ "      margin: 0;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    .ie-container table,\r\n"
				+ "    .mso-container table {\r\n"
				+ "      table-layout: fixed;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    * {\r\n"
				+ "      line-height: inherit;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    a[x-apple-data-detectors='true'] {\r\n"
				+ "      color: inherit !important;\r\n"
				+ "      text-decoration: none !important;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    table,\r\n"
				+ "    td {\r\n"
				+ "      color: #000000;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    #u_body a {\r\n"
				+ "      color: #0000ee;\r\n"
				+ "      text-decoration: underline;\r\n"
				+ "    }\r\n"
				+ "    \r\n"
				+ "    /* Estilos personalizados MEDMAIS */\r\n"
				+ "    .medmais-primary {\r\n"
				+ "      color: #2a5c8d !important;\r\n"
				+ "    }\r\n"
				+ "    .medmais-bg-primary {\r\n"
				+ "      background-color: #2a5c8d !important;\r\n"
				+ "    }\r\n"
				+ "    .medmais-secondary {\r\n"
				+ "      color: #4a90e2 !important;\r\n"
				+ "    }\r\n"
				+ "    .medmais-bg-secondary {\r\n"
				+ "      background-color: #4a90e2 !important;\r\n"
				+ "    }\r\n"
				+ "    .medmais-accent {\r\n"
				+ "      color: #ff6b35 !important;\r\n"
				+ "    }\r\n"
				+ "    .medmais-bg-accent {\r\n"
				+ "      background-color: #ff6b35 !important;\r\n"
				+ "    }\r\n"
				+ "  </style>\r\n"
				+ "  \r\n"
				+ "  <!--[if !mso]><!-->\r\n"
				+ "  <link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\r\n"
				+ "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\r\n"
				+ "  <!--<![endif]-->\r\n"
				+ "\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f5f9fc;color: #000000\">\r\n"
				+ "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\r\n"
				+ "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\r\n"
				+ "  <table id=\"u_body\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f5f9fc;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "    <tbody>\r\n"
				+ "      <tr style=\"vertical-align: top\">\r\n"
				+ "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\r\n"
				+ "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f5f9fc;\"><![endif]-->\r\n"
				+ "\r\n"
				+ "          <!-- Cabeçalho -->\r\n"
				+ "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #2a5c8d;\">\r\n"
				+ "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "                  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\r\n"
				+ "                      \r\n"
				+ "                      <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:30px 10px 15px;font-family:'Montserrat',sans-serif;\" align=\"center\">\r\n"
				+ "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
				+ "                                <tr>\r\n"
				+ "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\r\n"
				+ "                                    <img \r\n"
				+ "                                        src=[[LOGO]] \r\n"
				+ "                                        alt=\"MEDMAIS\"\r\n"
				+ "                                        style=\"width: 180px; height: auto;\"\r\n"
				+ "                                    />\r\n"
				+ "                                  </td>\r\n"
				+ "                                </tr>\r\n"
				+ "                              </table>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Montserrat',sans-serif;\" align=\"center\">\r\n"
				+ "                              <div style=\"font-size: 20px; color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\r\n"
				+ "                                <p style=\"font-size: 20px; line-height: 140%;\"><strong>ATUALIZAÇÃO DE SENHA</strong></p>\r\n"
				+ "                              </div>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                      </table>\r\n"
				+ "\r\n"
				+ "                    </div>\r\n"
				+ "                  </div>\r\n"
				+ "                </div>\r\n"
				+ "              </div>\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "\r\n"
				+ "          <!-- Corpo do Email -->\r\n"
				+ "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\r\n"
				+ "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "                  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\r\n"
				+ "                      \r\n"
				+ "                      <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 20px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "                              <div style=\"font-size: 16px; line-height: 160%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "                                <p style=\"font-size: 16px; line-height: 160%;\">Olá [[NOME]],</p>\r\n"
				+ "                                <p style=\"font-size: 16px; line-height: 160%; margin-top: 15px;\">Recebemos uma solicitação para atualizar a senha da sua conta na MEDMAIS. Para continuar, clique no botão abaixo:</p>\r\n"
				+ "                              </div>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                      </table>\r\n"
				+ "\r\n"
				+ "                      <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 40px;font-family:'Lato',sans-serif;\" align=\"center\">\r\n"
				+ "                              <div align=\"center\">\r\n"
				+ "                                <a href=\"[[LINK_REDEFINICAO]]\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #ff6b35; border-radius: 4px;-webkit-border-radius: 4px; -moz-border-radius: 4px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;font-size: 16px; padding: 15px 30px;\">\r\n"
				+ "                                  <span style=\"display:block;padding:15px 30px;line-height:120%;\"><strong>REDEFINIR SENHA</strong></span>\r\n"
				+ "                                </a>\r\n"
				+ "                              </div>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                      </table>\r\n"
				+ "\r\n"
				+ "                      <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 40px 40px;font-family:'Lato',sans-serif;\" align=\"left\">\r\n"
				+ "                              <div style=\"font-size: 16px; line-height: 160%; text-align: left; word-wrap: break-word;\">\r\n"
				+ "                                <p style=\"font-size: 16px; line-height: 160%;\">Se você não solicitou esta alteração, por favor ignore este e-mail ou entre em contato conosco imediatamente através do nosso suporte.</p>\r\n"
				+ "                                <p style=\"font-size: 16px; line-height: 160%; margin-top: 15px;\">O link expirará em 24 horas por motivos de segurança.</p>\r\n"
				+ "                                <p style=\"font-size: 16px; line-height: 160%; margin-top: 15px;\">Atenciosamente,<br>Equipe MEDMAIS</p>\r\n"
				+ "                              </div>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                      </table>\r\n"
				+ "\r\n"
				+ "                    </div>\r\n"
				+ "                  </div>\r\n"
				+ "                </div>\r\n"
				+ "              </div>\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "\r\n"
				+ "          <!-- Rodapé -->\r\n"
				+ "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\r\n"
				+ "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f1f5f9;\">\r\n"
				+ "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\r\n"
				+ "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\r\n"
				+ "                  <div style=\"height: 100%;width: 100% !important;\">\r\n"
				+ "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\r\n"
				+ "                      \r\n"
				+ "                      <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n"
				+ "                        <tbody>\r\n"
				+ "                          <tr>\r\n"
				+ "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Lato',sans-serif;\" align=\"center\">\r\n"
				+ "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">\r\n"
				+ "                                <p style=\"font-size: 14px; line-height: 160%;\">© 2023 MEDMAIS. Todos os direitos reservados.</p>\r\n"
				+ "                                <p style=\"font-size: 14px; line-height: 160%; margin-top: 10px;\">\r\n"
				+ "                                  <a href=\"https://medmais.com\" target=\"_blank\" style=\"color: #2a5c8d; text-decoration: underline;\">Nosso Site</a> | \r\n"
				+ "                                  <a href=\"https://medmais.com/contato\" target=\"_blank\" style=\"color: #2a5c8d; text-decoration: underline;\">Contato</a> | \r\n"
				+ "                                  <a href=\"https://medmais.com/privacidade\" target=\"_blank\" style=\"color: #2a5c8d; text-decoration: underline;\">Política de Privacidade</a>\r\n"
				+ "                                </p>\r\n"
				+ "                              </div>\r\n"
				+ "                            </td>\r\n"
				+ "                          </tr>\r\n"
				+ "                        </tbody>\r\n"
				+ "                      </table>\r\n"
				+ "\r\n"
				+ "                    </div>\r\n"
				+ "                  </div>\r\n"
				+ "                </div>\r\n"
				+ "              </div>\r\n"
				+ "            </div>\r\n"
				+ "          </div>\r\n"
				+ "\r\n"
				+ "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n"
				+ "        </td>\r\n"
				+ "      </tr>\r\n"
				+ "    </tbody>\r\n"
				+ "  </table>\r\n"
				+ "  <!--[if mso]></div><![endif]-->\r\n"
				+ "  <!--[if IE]></div><![endif]-->\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom(fromAddres, senderName);
        helper.setTo(toAddres);
        helper.setSubject(subject);
        
        content = content.replace("[[NOME]]", pessoa.getNome());  // Note que no HTML é [[NOME]]
        content = content.replace("[[LOGO]]", logo);
        content = content.replace("[[LINK_REDEFINICAO]]", this.frontTrocarSenhaURL + pessoa.getTokenConfirmacao());

        helper.setText(content, true);
        
        javaMailSender.send(message);	
		
	}

	public void trocarSenha(String token, String senha) {
		
        Pessoa pessoa = validacao(token);

        pessoa.setTokenConfirmacao(null);// 0 o token
        
		pessoa.setSenha(passwordUtil.encrypt(senha));
        
        pessoaRepository.save(pessoa);
        
	}
	
	private Pessoa validacao(String token) {
		
		Pessoa pessoa = pessoaRepository.findByTokenConfirmacao(token);
		
        if (pessoa == null) {
        	throw new IllegalArgumentException("Token invalido !");
        }
        
        if(pessoa.isTokenValido()) {
        	throw new IllegalArgumentException("Token expirado !");
        }
        		
		return pessoa;
	}

}

